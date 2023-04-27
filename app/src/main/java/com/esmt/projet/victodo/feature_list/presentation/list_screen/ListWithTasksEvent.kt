package com.esmt.projet.victodo.feature_list.presentation.list_screen

import com.esmt.projet.victodo.feature_task.domain.model.TaskWithTagAndSubTask

sealed class ListWithTasksEvent {
    data class OnDeleteClick(val taskWithTagAndSubTask: TaskWithTagAndSubTask) : ListWithTasksEvent()
    data class OnCompletedClick(val taskWithTagAndSubTask: TaskWithTagAndSubTask) : ListWithTasksEvent()
}