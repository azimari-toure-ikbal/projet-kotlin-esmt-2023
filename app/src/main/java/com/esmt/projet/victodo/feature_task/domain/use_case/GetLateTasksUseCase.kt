package com.esmt.projet.victodo.feature_task.domain.use_case

import com.esmt.projet.victodo.feature_task.domain.repository.TaskRepository

class GetLateTasksUseCase (
    val repository: TaskRepository
) {
    operator fun invoke() = repository.getLateTasks()
}