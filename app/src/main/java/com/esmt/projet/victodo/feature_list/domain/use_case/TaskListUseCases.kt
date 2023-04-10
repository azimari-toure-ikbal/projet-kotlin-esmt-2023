package com.esmt.projet.victodo.feature_list.domain.use_case

data class TaskListUseCases (
    val addTaskList: AddTaskList,
    val deleteTaskList: DeleteTaskList,
    val getTaskList: GetTaskList,
)