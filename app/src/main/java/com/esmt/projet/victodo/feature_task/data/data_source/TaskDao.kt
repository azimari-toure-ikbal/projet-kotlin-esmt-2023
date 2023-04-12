package com.esmt.projet.victodo.feature_task.data.data_source

import androidx.room.*
import com.esmt.projet.victodo.feature_tag.domain.model.Tag
import com.esmt.projet.victodo.feature_task.domain.model.SubTask
import com.esmt.projet.victodo.feature_task.domain.model.TagTaskCrossRef
import com.esmt.projet.victodo.feature_task.domain.model.Task
import com.esmt.projet.victodo.feature_task.domain.model.TaskWithTagsAndSubTasks
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {
    @Insert
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
    suspend fun insertTaskWithTagsAndSubTasks(taskWithTagsAndSubTasks: TaskWithTagsAndSubTasks) {
        val taskId = insertTask(taskWithTagsAndSubTasks.task)
        taskWithTagsAndSubTasks.tags.forEach { tag ->
            insertTagTaskCrossRef(TagTaskCrossRef(tgId = tag.id, tkId = taskId))
        }
        taskWithTagsAndSubTasks.subtasks.forEach { subTask ->
            insertSubTask(subTask.copy(taskId = taskId))
        }
    }

    @Delete
    suspend fun deleteTaskWithTagsAndSubTasks(taskWithTagsAndSubTasks: TaskWithTagsAndSubTasks) {
        deleteTask(taskWithTagsAndSubTasks.task)
        taskWithTagsAndSubTasks.tags.forEach { tag ->
            deleteTagTaskCrossRef(TagTaskCrossRef(tgId = tag.id, tkId = taskWithTagsAndSubTasks.task.id))
        }
        taskWithTagsAndSubTasks.subtasks.forEach { subTask ->
            deleteSubTask(subTask)
        }
    }

    @Transaction
    @Query("SELECT * FROM task")
    fun getTasksWithTagsAndSubTasks(): Flow<List<TaskWithTagsAndSubTasks>>

    @Transaction
    @Query("SELECT * FROM task WHERE id = :id")
    suspend fun getTaskWithTagsAndSubTasksById(id: Long): TaskWithTagsAndSubTasks?

    @Transaction
    @Query("SELECT * FROM task WHERE listId = :listId")
    fun getTasksWithTagsAndSubTasksByListId(listId: Long): Flow<List<TaskWithTagsAndSubTasks>>
}