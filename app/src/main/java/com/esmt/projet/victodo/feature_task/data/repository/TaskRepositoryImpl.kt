package com.esmt.projet.victodo.feature_task.data.repository

import com.esmt.projet.victodo.feature_task.data.data_source.TaskDao
import com.esmt.projet.victodo.feature_task.domain.model.TaskWithTagAndSubTask
import com.esmt.projet.victodo.feature_task.domain.repository.TaskRepository
import kotlinx.coroutines.flow.Flow

class TaskRepositoryImpl (
    private val taskDao: TaskDao,
): TaskRepository{
    override fun getAll(): Flow<List<TaskWithTagAndSubTask>> {
        return taskDao.getTasksWithTagsAndSubTasks()
    }

    override fun getAllByListId(listId: Long): Flow<List<TaskWithTagAndSubTask>> {
        return taskDao.getTasksWithTagsAndSubTasksByListId(listId)
    }

    override suspend fun getById(id: Long): TaskWithTagAndSubTask? {
        return taskDao.getTaskWithTagsAndSubTasksById(id)
    }

    override suspend fun insert(taskWithTagAndSubTask: TaskWithTagAndSubTask) {
        taskDao.insertTaskWithTagsAndSubTasks(taskWithTagAndSubTask)
    }

    override suspend fun delete(taskWithTagAndSubTask: TaskWithTagAndSubTask) {
        taskDao.deleteTaskWithTagsAndSubTasks(taskWithTagAndSubTask)
    }
}