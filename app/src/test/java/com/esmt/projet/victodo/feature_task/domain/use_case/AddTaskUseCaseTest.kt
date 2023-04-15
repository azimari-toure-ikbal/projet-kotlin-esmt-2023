package com.esmt.projet.victodo.feature_task.domain.use_case

import com.esmt.projet.victodo.feature_task.data.repository.FakeTaskRepository
import com.esmt.projet.victodo.feature_task.domain.model.Task
import com.esmt.projet.victodo.feature_task.domain.model.TaskWithTagAndSubTask

import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert.assertThat
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
                name="Faire du vélo"
            )
        )
        runBlocking {
            addTaskUseCase(taskWithTagAndSubTask)
            taskRepository.getAll().collect { tasks ->
                assertThat(tasks.contains(taskWithTagAndSubTask), CoreMatchers.equalTo(true))
            }
        }

    }
}