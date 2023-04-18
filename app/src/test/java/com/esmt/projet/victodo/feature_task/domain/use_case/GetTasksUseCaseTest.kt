package com.esmt.projet.victodo.feature_task.domain.use_case

import com.esmt.projet.victodo.feature_tag.domain.model.Tag
import com.esmt.projet.victodo.feature_task.data.repository.FakeTaskRepository
import com.esmt.projet.victodo.feature_task.domain.model.SubTask
import com.esmt.projet.victodo.feature_task.domain.model.Task
import com.esmt.projet.victodo.feature_task.domain.model.TaskWithTagAndSubTask
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.runBlocking

import org.junit.Before
import org.junit.Test

class GetTasksUseCaseTest {

    private lateinit var taskRepository : FakeTaskRepository
    private lateinit var getTasksUseCase: GetTasksUseCase
    private val tasks = listOf(
        TaskWithTagAndSubTask(
            task = Task(
                id = 1,
                name="Faire du vélo",
                listId = 1
            ),
            tags = listOf(
                Tag(
                    id = 1,
                    title = "maison"
                )
            ),
            subtasks = listOf(
                SubTask(
                    name = "Acheter un vélo"
                )
            )
        ),
        TaskWithTagAndSubTask(
            task = Task(
                name = "se venger",
                listId = 1
            )
        )
    )

    @Before
    fun setUp() {

        taskRepository = FakeTaskRepository()
        getTasksUseCase = GetTasksUseCase(repository = taskRepository)

        runBlocking {
            for (task in tasks){
                taskRepository.insertTask(task)
            }
        }
    }

    @Test
    fun `Should return all the tasks`()= runBlocking {
        getTasksUseCase().collect(){
            assertThat(it).isEqualTo(tasks)
        }
    }
}