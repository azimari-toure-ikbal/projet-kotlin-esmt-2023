package com.esmt.projet.victodo.feature_task.domain.use_case

import com.esmt.projet.victodo.exception.task.InvalidTaskException
import com.esmt.projet.victodo.feature_tag.domain.model.Tag
import com.esmt.projet.victodo.feature_task.data.repository.FakeTaskRepository

import com.esmt.projet.victodo.feature_task.domain.model.SubTask
import com.esmt.projet.victodo.feature_task.domain.model.Task
import com.esmt.projet.victodo.feature_task.domain.model.TaskWithTagAndSubTask
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertThrows


import org.junit.Before
import org.junit.Test

class DeleteTaskUseCaseTest {

    private lateinit var deleteTaskUseCase: DeleteTaskUseCase
    private lateinit var taskRepository: FakeTaskRepository

    @Before
    fun setUp() {
        taskRepository = FakeTaskRepository()
        deleteTaskUseCase = DeleteTaskUseCase(repository = taskRepository)
        runBlocking {
            taskRepository.insertTask(
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
                )
            )
        }
    }

    @Test
    fun `Should delete a Task with a non null id`(){
        val taskToDelete = TaskWithTagAndSubTask(
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
        )
        runBlocking {
            deleteTaskUseCase(taskToDelete)
            taskRepository.getTasks().collect(){
                assertThat(it).doesNotContain(taskToDelete)
            }
        }
    }

    @Test
    fun `Should throw exception when trying to delete a Task with invalid id`(){
        val taskToDelete = TaskWithTagAndSubTask(
            task = Task(
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
        )
        val exception = assertThrows(InvalidTaskException::class.java){
            runBlocking {
                deleteTaskUseCase(taskToDelete)
            }
        }
        assertThat(exception).hasMessageThat().isEqualTo("Task to delete must have id")
    }
}