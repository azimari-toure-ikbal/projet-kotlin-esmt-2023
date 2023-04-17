package com.esmt.projet.victodo.feature_task.domain.use_case

import com.esmt.projet.victodo.feature_task.domain.repository.TaskRepository

class GetCompletedTasksUseCase(
    val repository: TaskRepository
) {
    operator fun invoke() = repository.getCompletedTasks()
}