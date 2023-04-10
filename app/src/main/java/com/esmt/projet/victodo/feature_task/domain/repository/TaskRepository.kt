package com.esmt.projet.victodo.feature_task.domain.repository

import com.esmt.projet.victodo.feature_task.domain.model.TaskWithTagsAndSubTasks
import kotlinx.coroutines.flow.Flow

interface TaskRepository {
    fun getAll(): Flow<List<TaskWithTagsAndSubTasks>>

    fun getAllByListId(listId: Long): Flow<List<TaskWithTagsAndSubTasks>>

    suspend fun getById(id: Long): TaskWithTagsAndSubTasks?

    suspend fun insert(taskWithTagsAndSubTasks: TaskWithTagsAndSubTasks)

    suspend fun delete(taskWithTagsAndSubTasks: TaskWithTagsAndSubTasks)
}