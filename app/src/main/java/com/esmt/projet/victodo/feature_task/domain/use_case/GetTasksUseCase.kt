package com.esmt.projet.victodo.feature_task.domain.use_case

import com.esmt.projet.victodo.feature_task.domain.model.TaskWithTagAndSubTask
import com.esmt.projet.victodo.feature_task.domain.repository.TaskRepository
import kotlinx.coroutines.flow.Flow

class GetTasksUseCase (
    val repository: TaskRepository
) {
    operator fun invoke(): Flow<List<TaskWithTagAndSubTask>> = repository.getTasks()
}