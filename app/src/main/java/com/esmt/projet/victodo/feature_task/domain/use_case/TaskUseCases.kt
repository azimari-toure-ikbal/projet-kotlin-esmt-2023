package com.esmt.projet.victodo.feature_task.domain.use_case

data class TaskUseCases (
    val addTaskUseCase: AddTaskUseCase,
    val deleteTaskUseCase: DeleteTaskUseCase,
    val getTaskUseCase: GetTaskUseCase,
    val getTasksUseCase: GetTasksUseCase
)