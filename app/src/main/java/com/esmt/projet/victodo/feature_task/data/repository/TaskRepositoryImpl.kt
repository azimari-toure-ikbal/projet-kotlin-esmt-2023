package com.esmt.projet.victodo.feature_task.data.repository

import com.esmt.projet.victodo.feature_task.data.data_source.TaskDao
import com.esmt.projet.victodo.feature_task.domain.model.TaskWithTagsAndSubTasks
import com.esmt.projet.victodo.feature_task.domain.repository.TaskRepository
import kotlinx.coroutines.flow.Flow

class TaskRepositoryImpl (
    private val taskDao: TaskDao,
): TaskRepository{
    override fun getAll(): Flow<List<TaskWithTagsAndSubTasks>> {
        return taskDao.getTasksWithTagsAndSubTasks()
    }

    override fun getAllByListId(listId: Long): Flow<List<TaskWithTagsAndSubTasks>> {
        return taskDao.getTasksWithTagsAndSubTasksByListId(listId)
    }

    override suspend fun getById(id: Long): TaskWithTagsAndSubTasks? {
        return taskDao.getTaskWithTagsAndSubTasksById(id)
    }

    override suspend fun insert(taskWithTagsAndSubTasks: TaskWithTagsAndSubTasks) {
        taskDao.insertTaskWithTagsAndSubTasks(taskWithTagsAndSubTasks)
    }

    override suspend fun delete(taskWithTagsAndSubTasks: TaskWithTagsAndSubTasks) {
        taskDao.deleteTaskWithTagsAndSubTasks(taskWithTagsAndSubTasks)
    }
}