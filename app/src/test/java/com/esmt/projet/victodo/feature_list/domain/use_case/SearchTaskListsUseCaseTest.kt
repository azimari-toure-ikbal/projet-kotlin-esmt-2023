package com.esmt.projet.victodo.feature_list.domain.use_case

import com.esmt.projet.victodo.feature_list.data.repository.FakeTaskListRepository
import com.esmt.projet.victodo.feature_list.domain.model.TaskList
import com.esmt.projet.victodo.feature_list.domain.model.TaskListWithTasksAndTagsSubTasks
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*

import org.junit.Before
import org.junit.Test

class SearchTaskListsUseCaseTest {

    private lateinit var searchTaskListsUseCase: SearchTaskListsUseCase
    private lateinit var repository: FakeTaskListRepository
    private val startList = listOf<TaskListWithTasksAndTagsSubTasks>(
        TaskListWithTasksAndTagsSubTasks(
            taskList = TaskList(
                id = 1,
                title = "List 1",
                color = 1,
                icon = "ic_baseline_list_24",
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
                icon = "ic_baseline_list_24",
                isPinned = false,
                isDefault = false
            ),
            tasks = listOf(),
        ),
        TaskListWithTasksAndTagsSubTasks(
            taskList = TaskList(
                id = 3,
                title = "List 2 prime",
                color = 3,
                icon = "ic_baseline_list_24",
                isPinned = false,
                isDefault = false
            ),
            tasks = listOf(),
        )

    )


    @Before
    fun setUp() {
        repository = FakeTaskListRepository()
        searchTaskListsUseCase = SearchTaskListsUseCase(repository)

        //add some data to the repository

        startList.forEachIndexed() { _, taskList ->
            runBlocking {
                repository.insertList(taskList.taskList)
            }
        }
    }

    @Test
    fun `searchLists should return all the lists`() = runBlocking {
        val result = searchTaskListsUseCase("prim").first()
        assertEquals(1, result.toList().size)
    }

}

