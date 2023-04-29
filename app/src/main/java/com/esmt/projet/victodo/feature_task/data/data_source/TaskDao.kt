package com.esmt.projet.victodo.feature_task.data.data_source

import androidx.room.*
import com.esmt.projet.victodo.feature_task.domain.model.SubTask
import com.esmt.projet.victodo.feature_task.domain.model.TagTaskCrossRef
import com.esmt.projet.victodo.feature_task.domain.model.Task
import com.esmt.projet.victodo.feature_task.domain.model.TaskWithTagAndSubTask
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTask(task: Task): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSubTask(subTask: SubTask): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTagTaskCrossRef(tagTaskCrossRef: TagTaskCrossRef)

    @Delete
    suspend fun deleteTask(task: Task)

    @Delete
    suspend fun deleteSubTask(subTask: SubTask)

    @Delete
    suspend fun deleteTagTaskCrossRef(tagTaskCrossRef: TagTaskCrossRef)

    @Transaction
    suspend fun insertTaskWithTagsAndSubTasks(taskWithTagAndSubTask: TaskWithTagAndSubTask): Long {
        val taskId = insertTask(taskWithTagAndSubTask.task)
        taskWithTagAndSubTask.tags?.forEach { tag ->
            insertTagTaskCrossRef(TagTaskCrossRef(tgId = tag.id!!, tkId = taskId))
        }
        taskWithTagAndSubTask.subtasks?.forEach { subTask ->
            insertSubTask(subTask.copy(taskId = taskId))
        }
        return taskId
    }

    @Delete
    suspend fun deleteTaskWithTagsAndSubTasks(taskWithTagAndSubTask: TaskWithTagAndSubTask) {
        deleteTask(taskWithTagAndSubTask.task)
        taskWithTagAndSubTask.tags?.forEach { tag ->
            deleteTagTaskCrossRef(TagTaskCrossRef(tgId = tag.id!!, tkId = taskWithTagAndSubTask.task.id!!))
        }
        taskWithTagAndSubTask.subtasks?.forEach { subTask ->
            deleteSubTask(subTask)
        }
    }

    @Transaction
    @Query("SELECT * FROM task")
    fun getTasksWithTagsAndSubTasks(): Flow<List<TaskWithTagAndSubTask>>

    @Transaction
    @Query("SELECT * FROM task WHERE id = :id")
    suspend fun getTaskWithTagsAndSubTasksById(id: Long): TaskWithTagAndSubTask?

    @Transaction
    @Query("SELECT * FROM task WHERE listId = :listId")
    fun getTasksWithTagsAndSubTasksByListId(listId: Long): Flow<List<TaskWithTagAndSubTask>>

    @Transaction
    @Query(
        "SELECT * FROM task WHERE dueDate IS NOT NULL AND dueDate >= date() AND isEnded = 'false'"
    )
    fun getScheduledTasksWithTagsAndSubTasks(): Flow<List<TaskWithTagAndSubTask>>

    @Transaction
    @Query("SELECT * FROM task WHERE isEnded = 'true'")
    fun getCompletedTasksWithTagsAndSubTasks(): Flow<List<TaskWithTagAndSubTask>>

    @Transaction
    @Query("SELECT * FROM task WHERE dueDate IS NOT NULL AND dueDate < date() AND isEnded = 'false'")
    fun getLateTasksWithTagsAndSubTask(): Flow<List<TaskWithTagAndSubTask>>

    @Transaction
    @Query("SELECT * FROM task WHERE dueDate = date()")
    fun getTodayTasksWithTagsAndSubTask(): Flow<List<TaskWithTagAndSubTask>>
}