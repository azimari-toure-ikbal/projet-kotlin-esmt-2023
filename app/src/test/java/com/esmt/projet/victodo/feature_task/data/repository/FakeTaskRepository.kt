package com.esmt.projet.victodo.feature_task.data.repository

import com.esmt.projet.victodo.feature_task.domain.model.TaskWithTagAndSubTask
import com.esmt.projet.victodo.feature_task.domain.repository.TaskRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeTaskRepository: TaskRepository {

    private val taskWithTagAndSubTasks = mutableListOf<TaskWithTagAndSubTask>()

    override fun getAll(): Flow<List<TaskWithTagAndSubTask>> {
        return flow {
            emit(taskWithTagAndSubTasks)
        }
    }

    override fun getAllByListId(listId: Long): Flow<List<TaskWithTagAndSubTask>> {
        return flow {
            emit(
                taskWithTagAndSubTasks.filter {
                    it.task.listId == listId
                }
            )
        }
    }

    override suspend fun getById(id: Long): TaskWithTagAndSubTask? {
        return taskWithTagAndSubTasks.find {
            it.task.id == id
        }
    }

    override suspend fun insert(taskWithTagAndSubTask: TaskWithTagAndSubTask) {
        taskWithTagAndSubTasks.add(taskWithTagAndSubTask)
    }

    override suspend fun delete(taskWithTagAndSubTask: TaskWithTagAndSubTask) {
        taskWithTagAndSubTasks.remove(taskWithTagAndSubTask)
    }

}