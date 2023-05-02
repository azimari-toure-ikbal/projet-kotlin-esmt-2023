package com.esmt.projet.victodo.feature_list.presentation.list_screen

import com.esmt.projet.victodo.feature_task.domain.model.TaskWithTagAndSubTask

data class ListWithTasksState(
    val listOfTasks: List<TaskWithTagAndSubTask> = emptyList(),
    val listOfNonCompletedTask: List<TaskWithTagAndSubTask> = emptyList(),
    val listOfCompletedTask: List<TaskWithTagAndSubTask> = emptyList(),
    val isTaskLoading: Boolean = true
)