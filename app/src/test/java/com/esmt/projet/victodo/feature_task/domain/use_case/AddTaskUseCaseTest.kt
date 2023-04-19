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

class AddTaskUseCaseTest {
    private lateinit var addTaskUseCase: AddTaskUseCase
    private lateinit var taskRepository: FakeTaskRepository

    @Before
    fun setup(){
        taskRepository = FakeTaskRepository()
        addTaskUseCase = AddTaskUseCase(repository = taskRepository)
    }

    @Test
    fun `Should correctly add a valid task in the repo`(){
        val taskWithTagAndSubTask = TaskWithTagAndSubTask(
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
        runBlocking {
            addTaskUseCase(taskWithTagAndSubTask)
            taskRepository.getTasks().collect { tasks ->
                assertThat(tasks).contains(taskWithTagAndSubTask)
            }
        }
    }

    @Test
    fun `Should throw exception when adding Task without name`(){
        val taskWithTagAndSubTask = TaskWithTagAndSubTask(
            task = Task(
                name = "",
                listId = 1
            )
        )
        val e= assertThrows(
            InvalidTaskException::class.java
        ) {
            runBlocking {
                addTaskUseCase(taskWithTagAndSubTask)
            }
        }
        assertThat(e).hasMessageThat().startsWith("Task must have a title")
    }

    @Test
    fun `Should throw exception when adding Task without listId`(){
        val taskWithTagAndSubTask = TaskWithTagAndSubTask(
            task = Task(
                name = "Cuisiner"
            )
        )
        val e= assertThrows(InvalidTaskException::class.java
        ) {
            runBlocking {
                addTaskUseCase(taskWithTagAndSubTask)
            }
        }
        assertThat(e).hasMessageThat().startsWith("Task must be associated with a list")
    }

    @Test
    fun `Should throw exception when adding Task whit tag without id`(){
        val taskWithTagAndSubTask = TaskWithTagAndSubTask(
            task = Task(
                name = "Cuisiner",
                listId = 1
            ),
            tags = listOf(
                Tag(title = "sport")
            )
        )
        val e= assertThrows(InvalidTaskException::class.java
        ) {
            runBlocking {
                addTaskUseCase(taskWithTagAndSubTask)
            }
        }
        assertThat(e).hasMessageThat().startsWith("Tag must have an id")
    }

    @Test
    fun `Should throw exception when adding Task whit subtask without name`(){
        val taskWithTagAndSubTask = TaskWithTagAndSubTask(
            task = Task(
                name = "Cuisiner",
                listId = 1
            ),
            subtasks = listOf(
                SubTask(
                    name = ""
                )
            )
        )
        val e= assertThrows(InvalidTaskException::class.java
        ) {
            runBlocking {
                addTaskUseCase(taskWithTagAndSubTask)
            }
        }
        assertThat(e).hasMessageThat().startsWith("SubTask must have a title")
    }
}