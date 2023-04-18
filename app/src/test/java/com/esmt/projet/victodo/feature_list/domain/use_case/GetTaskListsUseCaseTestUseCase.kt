package com.esmt.projet.victodo.feature_list.domain.use_case

import com.esmt.projet.victodo.feature_list.data.repository.FakeTaskListRepository
import com.esmt.projet.victodo.feature_list.domain.model.TaskList
import com.esmt.projet.victodo.feature_list.domain.model.TaskListWithTasksAndTagsSubTasks
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before

import org.junit.Test

class GetTaskListsUseCaseTestUseCase {

    private lateinit var getTaskListsUseCase: GetTaskListsUseCase
    private lateinit var repository: FakeTaskListRepository
    private val startList = listOf<TaskListWithTasksAndTagsSubTasks>(
        TaskListWithTasksAndTagsSubTasks(
            taskList = TaskList(
                id = 1,
                title = "List 1",
                color = 1,
                icon = 0,
                isPinned = false,
                isDefault = false
            ),
            tasks = listOf(),
        ),
        TaskListWithTasksAndTagsSubTasks(
            taskList = TaskList(
                id = 2,
                title = "List 2",
                color = 2,
                icon = 1,
                isPinned = false,
                isDefault = false
            ),
            tasks = listOf(),
        ),
    )


    @Before
    fun setUp() {
        repository = FakeTaskListRepository()
        getTaskListsUseCase = GetTaskListsUseCase(repository)

        //add some data to the repository

        startList.forEachIndexed() { _, taskList ->
            runBlocking {
                repository.insertList(taskList.taskList)
            }
        }
    }

    @Test
    fun `getTaskList should return all the lists`() = runBlocking {
        val result = getTaskListsUseCase().first()

        startList.forEachIndexed() { index, taskList ->
            assertEquals(taskList, result[index])
        }
    }

}