package com.esmt.projet.victodo.feature_list.data.repository

import com.esmt.projet.victodo.feature_list.domain.model.TaskList
import com.esmt.projet.victodo.feature_list.domain.model.TaskListWithTasksAndTagsSubTasks
import com.esmt.projet.victodo.feature_list.domain.repository.TaskListRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeTaskListRepository: TaskListRepository{

    private val lists = mutableListOf<TaskListWithTasksAndTagsSubTasks>()

    override fun getLists(): Flow<List<TaskListWithTasksAndTagsSubTasks>> {

        return flow { emit(lists) }
    }

    override suspend fun getListById(id: Long): TaskListWithTasksAndTagsSubTasks{
        return lists.find { it.taskList.id == id }!!
    }

    override suspend fun insertList(list: TaskList) {
        lists.add(TaskListWithTasksAndTagsSubTasks(list, emptyList()))
    }

    override suspend fun deleteList(list: TaskList) {
        lists.minus(lists.find { it.taskList.id == list.id })
    }

    override fun searchLists(name: String): Flow<List<TaskListWithTasksAndTagsSubTasks>> {
        return flow { emit(lists.filter { it.taskList.title.contains(name) }) }
    }
}