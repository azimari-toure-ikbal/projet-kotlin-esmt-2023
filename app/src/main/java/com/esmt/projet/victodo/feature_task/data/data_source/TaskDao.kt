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
        taskWithTagAndSubTask.tags.forEach { tag ->
            insertTagTaskCrossRef(TagTaskCrossRef(tgId = tag.id!!, tkId = taskId))
        }
        taskWithTagAndSubTask.subtasks.forEach { subTask ->
            insertSubTask(subTask.copy(taskId = taskId))
        }
        return taskId
    }

    @Delete
    suspend fun deleteTaskWithTagsAndSubTasks(taskWithTagAndSubTask: TaskWithTagAndSubTask) {
        deleteTask(taskWithTagAndSubTask.task)
        taskWithTagAndSubTask.tags.forEach { tag ->
            deleteTagTaskCrossRef(TagTaskCrossRef(tgId = tag.id!!, tkId = taskWithTagAndSubTask.task.id!!))
        }
        taskWithTagAndSubTask.subtasks.forEach { subTask ->
            deleteSubTask(subTask)
        }
    }

    @Transaction
    @Query("SELECT * FROM task ORDER BY timestamp DESC")
    fun getTasksWithTagsAndSubTasks(): Flow<List<TaskWithTagAndSubTask>>

    @Transaction
    @Query("SELECT * FROM task WHERE id = :id ")
    suspend fun getTaskWithTagsAndSubTasksById(id: Long): TaskWithTagAndSubTask?

    @Transaction
    @Query("SELECT * FROM task WHERE listId = :listId ORDER BY timestamp DESC")
    fun getTasksWithTagsAndSubTasksByListId(listId: Long): Flow<List<TaskWithTagAndSubTask>>

    @Transaction
    @Query(
        "SELECT * FROM task WHERE dueDate IS NOT NULL AND ((dueDate > strftime('%m-%d-%Y', date('now')) ) OR (dueDate = strftime('%m-%d-%Y', date('now')) AND dueTime > strftime('%H:%M', time('now')))) AND isEnded = 0 ORDER BY dueDate ASC, dueTime ASC"
    )
    fun getScheduledTasksWithTagsAndSubTasks(): Flow<List<TaskWithTagAndSubTask>>

    @Transaction
    @Query("SELECT * FROM task WHERE isEnded = 1 ORDER BY timestamp DESC")
    fun getCompletedTasksWithTagsAndSubTasks(): Flow<List<TaskWithTagAndSubTask>>

    @Transaction
    @Query("SELECT * FROM task WHERE dueDate IS NOT NULL AND dueDate <= strftime('%m-%d-%Y', date('now')) AND dueTime <= strftime('%H:%M', time('now')) AND isEnded = 0 ORDER BY dueDate ASC, dueTime ASC")
    fun getLateTasksWithTagsAndSubTask(): Flow<List<TaskWithTagAndSubTask>>

    @Transaction
    @Query("SELECT * FROM task WHERE dueDate = strftime('%m-%d-%Y', date('now')) AND dueTime > strftime('%H:%M', time('now')) AND isEnded = 0 ORDER BY dueDate ASC, dueTime ASC")
    fun getTodayTasksWithTagsAndSubTask(): Flow<List<TaskWithTagAndSubTask>>
}