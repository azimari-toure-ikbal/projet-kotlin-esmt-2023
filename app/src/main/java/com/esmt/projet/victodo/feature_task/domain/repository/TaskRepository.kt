package com.esmt.projet.victodo.feature_task.domain.repository

import com.esmt.projet.victodo.feature_task.domain.model.TaskWithTagAndSubTask
import kotlinx.coroutines.flow.Flow

interface TaskRepository {
    fun getAll(): Flow<List<TaskWithTagAndSubTask>>

    fun getAllByListId(listId: Long): Flow<List<TaskWithTagAndSubTask>>

    suspend fun getById(id: Long): TaskWithTagAndSubTask?

    suspend fun insert(taskWithTagAndSubTask: TaskWithTagAndSubTask)

    suspend fun delete(taskWithTagAndSubTask: TaskWithTagAndSubTask)
}