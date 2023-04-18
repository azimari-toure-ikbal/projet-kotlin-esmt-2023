package com.esmt.projet.victodo.feature_task.domain.use_case

import com.esmt.projet.victodo.feature_task.domain.repository.TaskRepository

class GetScheduledTasksUseCase(
    val repository: TaskRepository
) {
    operator fun invoke() = repository.getScheduledTasks()
}