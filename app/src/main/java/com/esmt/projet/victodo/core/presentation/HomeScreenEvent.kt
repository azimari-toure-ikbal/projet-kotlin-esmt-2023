package com.esmt.projet.victodo.core.presentation

import androidx.compose.ui.focus.FocusState
import com.esmt.projet.victodo.feature_list.domain.model.TaskListWithTasksAndTagsSubTasks

sealed class HomeScreenEvent {
//    data class onEditClicked(val id: Int) : HomeScreenEvent()
    data class OnSupprimerClicked(val taskList: TaskListWithTasksAndTagsSubTasks) : HomeScreenEvent()
    data class OnSearch(val query: String) : HomeScreenEvent()
    data class OnSearchFocusChanged(val focusState: FocusState) : HomeScreenEvent()
    object OnTagRevealClicked : HomeScreenEvent()
}