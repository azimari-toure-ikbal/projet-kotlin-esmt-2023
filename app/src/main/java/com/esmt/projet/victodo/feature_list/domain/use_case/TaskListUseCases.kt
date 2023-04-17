package com.esmt.projet.victodo.feature_list.domain.use_case

data class TaskListUseCases (
    val addTaskListUseCase: AddTaskListUseCase,
    val deleteTaskListUseCase: DeleteTaskListUseCase,
    val getTaskListUseCase: GetTaskListUseCase,
    val searchTaskList: SearchTaskListsUseCase
)