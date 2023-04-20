package com.esmt.projet.victodo.feature_task.data.repository

import com.esmt.projet.victodo.feature_task.data.data_source.TaskDao
import com.esmt.projet.victodo.feature_task.domain.model.TaskWithTagAndSubTask
import com.esmt.projet.victodo.feature_task.domain.repository.TaskRepository
import kotlinx.coroutines.flow.Flow

class TaskRepositoryImpl (
    private val taskDao: TaskDao,
): TaskRepository{
    override fun getTasks(): Flow<List<TaskWithTagAndSubTask>> {
        return taskDao.getTasksWithTagsAndSubTasks()
    }

    override fun getTasksByListId(listId: Long): Flow<List<TaskWithTagAndSubTask>> {
        return taskDao.getTasksWithTagsAndSubTasksByListId(listId)
    }

    override suspend fun getTaskById(id: Long): TaskWithTagAndSubTask? {
        return taskDao.getTaskWithTagsAndSubTasksById(id)
    }

    override suspend fun insertTask(taskWithTagAndSubTask: TaskWithTagAndSubTask): Long {
        return taskDao.insertTaskWithTagsAndSubTasks(taskWithTagAndSubTask)
    }

    override suspend fun deleteTask(taskWithTagAndSubTask: TaskWithTagAndSubTask) {
        taskDao.deleteTaskWithTagsAndSubTasks(taskWithTagAndSubTask)
    }

    override fun getScheduledTasks(): Flow<List<TaskWithTagAndSubTask>> {
        return taskDao.getScheduledTasksWithTagsAndSubTasks()
    }

    override fun getTodayTasks(): Flow<List<TaskWithTagAndSubTask>> {
        return taskDao.getTodayTasksWithTagsAndSubTask()
    }

    override fun getLateTasks(): Flow<List<TaskWithTagAndSubTask>> {
        return taskDao.getLateTasksWithTagsAndSubTask()
    }

    override fun getCompletedTasks(): Flow<List<TaskWithTagAndSubTask>> {
        return taskDao.getCompletedTasksWithTagsAndSubTasks()
    }
}