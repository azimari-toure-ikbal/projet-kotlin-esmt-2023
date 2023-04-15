package com.esmt.projet.victodo.feature_task.domain.use_case

import com.esmt.projet.victodo.feature_task.domain.model.TaskWithTagAndSubTask
import com.esmt.projet.victodo.feature_task.domain.repository.TaskRepository

class DeleteTaskUseCase(
    val repository: TaskRepository
) {
    suspend operator fun invoke(taskWithTagAndSubTask: TaskWithTagAndSubTask) =
        repository.delete(taskWithTagAndSubTask)
}