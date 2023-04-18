package com.esmt.projet.victodo.feature_task.domain.use_case

import com.esmt.projet.victodo.feature_task.domain.model.TaskWithTagAndSubTask
import com.esmt.projet.victodo.feature_task.domain.repository.TaskRepository

class GetTaskUseCase (
    val repository: TaskRepository
) {
    suspend operator fun invoke(id: Long): TaskWithTagAndSubTask? = repository.getTaskById(id)
}