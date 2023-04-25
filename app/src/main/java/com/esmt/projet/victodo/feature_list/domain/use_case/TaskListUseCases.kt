package com.esmt.projet.victodo.feature_list.domain.use_case

data class TaskListUseCases (
    val addTaskListUseCase: AddTaskListUseCase,
    val deleteTaskListUseCase: DeleteTaskListUseCase,
    val getTaskListsUseCase: GetTaskListsUseCase,
    val searchTaskList: SearchTaskListsUseCase,
    val getTaskListUseCase: GetTaskListUseCase,
    val getUtilTaskListsUseCase: GetUtilTaskListsUseCase,
    val getListsUseCase: GetListsUseCase
)