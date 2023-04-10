package com.esmt.projet.victodo.feature_list.data.repository

import com.esmt.projet.victodo.feature_list.data.data_source.TaskListDao
import com.esmt.projet.victodo.feature_list.domain.model.TaskListWithTasksAndTagsSubTasks
import com.esmt.projet.victodo.feature_list.domain.repository.TaskListRepository
import kotlinx.coroutines.flow.Flow

class TaskListRepositoryImpl(
    private val taskListDao: TaskListDao
): TaskListRepository{
    override fun getLists(): Flow<List<TaskListWithTasksAndTagsSubTasks>> {
        return taskListDao.getLists()
    }

    override suspend fun getListById(id: Long): TaskListWithTasksAndTagsSubTasks? {
        return taskListDao.getListById(id)
    }

    override suspend fun insertList(list: TaskListWithTasksAndTagsSubTasks) {
        return taskListDao.insertList(list)
    }

    override suspend fun deleteList(list: TaskListWithTasksAndTagsSubTasks) {
        return taskListDao.deleteList(list)
    }
}