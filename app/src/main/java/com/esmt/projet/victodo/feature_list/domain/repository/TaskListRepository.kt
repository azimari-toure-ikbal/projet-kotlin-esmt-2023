package com.esmt.projet.victodo.feature_list.domain.repository

import com.esmt.projet.victodo.feature_list.domain.model.TaskList
import com.esmt.projet.victodo.feature_list.domain.model.TaskListWithTasksAndTagsSubTasks
import kotlinx.coroutines.flow.Flow

interface TaskListRepository {

    fun getLists(): Flow<List<TaskListWithTasksAndTagsSubTasks>>

    suspend fun getListById(id: Long): TaskListWithTasksAndTagsSubTasks

    suspend fun insertList(list: TaskList)

    suspend fun deleteList(list: TaskList)
    fun searchLists(name: String): Flow<List<TaskListWithTasksAndTagsSubTasks>>
}