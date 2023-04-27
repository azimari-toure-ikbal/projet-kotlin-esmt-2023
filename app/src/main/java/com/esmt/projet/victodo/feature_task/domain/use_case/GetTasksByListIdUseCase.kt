package com.esmt.projet.victodo.feature_task.domain.use_case

import com.esmt.projet.victodo.feature_task.domain.repository.TaskRepository

class GetTasksByListIdUseCase(
    private val taskRepository: TaskRepository
) {
    operator fun invoke(listId: Long) = taskRepository.getTasksByListId(listId)
}