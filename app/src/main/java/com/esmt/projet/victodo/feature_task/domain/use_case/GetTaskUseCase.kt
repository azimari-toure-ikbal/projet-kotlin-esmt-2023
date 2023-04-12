package com.esmt.projet.victodo.feature_task.domain.use_case

import com.esmt.projet.victodo.feature_task.domain.model.TaskWithTagsAndSubTasks
import com.esmt.projet.victodo.feature_task.domain.repository.TaskRepository

class GetTaskUseCase (
    val repository: TaskRepository
) {
    suspend operator fun invoke(id: Long): TaskWithTagsAndSubTasks? = repository.getById(id)
}