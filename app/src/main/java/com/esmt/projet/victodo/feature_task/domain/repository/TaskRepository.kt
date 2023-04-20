package com.esmt.projet.victodo.feature_task.domain.repository

import com.esmt.projet.victodo.feature_task.domain.model.TaskWithTagAndSubTask
import kotlinx.coroutines.flow.Flow

interface TaskRepository {
    fun getTasks(): Flow<List<TaskWithTagAndSubTask>>

    fun getTasksByListId(listId: Long): Flow<List<TaskWithTagAndSubTask>>

    suspend fun getTaskById(id: Long): TaskWithTagAndSubTask?

    suspend fun insertTask(taskWithTagAndSubTask: TaskWithTagAndSubTask): Long

    suspend fun deleteTask(taskWithTagAndSubTask: TaskWithTagAndSubTask)

    fun getScheduledTasks(): Flow<List<TaskWithTagAndSubTask>>

    fun getTodayTasks(): Flow<List<TaskWithTagAndSubTask>>

    fun getLateTasks(): Flow<List<TaskWithTagAndSubTask>>

    fun getCompletedTasks(): Flow<List<TaskWithTagAndSubTask>>
}