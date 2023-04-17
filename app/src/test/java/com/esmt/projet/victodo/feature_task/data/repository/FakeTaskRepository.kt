package com.esmt.projet.victodo.feature_task.data.repository

import com.esmt.projet.victodo.feature_task.domain.model.TaskWithTagAndSubTask
import com.esmt.projet.victodo.feature_task.domain.repository.TaskRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeTaskRepository: TaskRepository {

    private val taskWithTagAndSubTasks = mutableListOf<TaskWithTagAndSubTask>()

    override fun getTasks(): Flow<List<TaskWithTagAndSubTask>> {
        return flow {
            emit(taskWithTagAndSubTasks)
        }
    }

    override fun getTasksByListId(listId: Long): Flow<List<TaskWithTagAndSubTask>> {
        return flow {
            emit(
                taskWithTagAndSubTasks.filter {
                    it.task.listId == listId
                }
            )
        }
    }

    override suspend fun getTaskById(id: Long): TaskWithTagAndSubTask? {
        return taskWithTagAndSubTasks.find {
            it.task.id == id
        }
    }

    override suspend fun insertTask(taskWithTagAndSubTask: TaskWithTagAndSubTask) {
        taskWithTagAndSubTasks.add(taskWithTagAndSubTask)
    }

    override suspend fun deleteTask(taskWithTagAndSubTask: TaskWithTagAndSubTask) {
        taskWithTagAndSubTasks.remove(taskWithTagAndSubTask)
    }

    override fun getScheduledTasks(): Flow<List<TaskWithTagAndSubTask>> {
        TODO("Not yet implemented")
    }

    override fun getTodayTasks(): Flow<List<TaskWithTagAndSubTask>> {
        TODO("Not yet implemented")
    }

    override fun getLateTasks(): Flow<List<TaskWithTagAndSubTask>> {
        TODO("Not yet implemented")
    }

    override fun getCompletedTasks(): Flow<List<TaskWithTagAndSubTask>> {
        TODO("Not yet implemented")
    }

}