package com.esmt.projet.victodo.feature_task.domain.use_case

import com.esmt.projet.victodo.feature_task.domain.model.TaskWithTagsAndSubTasks
import com.esmt.projet.victodo.feature_task.domain.repository.TaskRepository

class DeleteTaskUseCase(
    val repository: TaskRepository
) {
    suspend operator fun invoke(taskWithTagsAndSubTasks: TaskWithTagsAndSubTasks) =
        repository.delete(taskWithTagsAndSubTasks)
}