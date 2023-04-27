package com.esmt.projet.victodo.feature_task.domain.use_case

data class TaskUseCases (
    val addTaskUseCase: AddTaskUseCase,
    val deleteTaskUseCase: DeleteTaskUseCase,
    val getTaskUseCase: GetTaskUseCase,
    val getTasksUseCase: GetTasksUseCase,
    val getCompletedTasksUseCase: GetCompletedTasksUseCase,
    val getScheduledTasksUseCase: GetScheduledTasksUseCase,
    val getLateTasksUseCase: GetLateTasksUseCase,
    val getTodayTasksUseCase: GetTodayTasksUseCase,
    val getTasksByListIdUseCase: GetTasksByListIdUseCase
)