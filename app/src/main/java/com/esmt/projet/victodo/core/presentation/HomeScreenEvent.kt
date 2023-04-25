package com.esmt.projet.victodo.core.presentation

import androidx.compose.ui.focus.FocusState
import com.esmt.projet.victodo.feature_list.domain.model.TaskListWithTasksAndTagsSubTasks

sealed class HomeScreenEvent {
    data class onEditClicked(val id: Int) : HomeScreenEvent()
    data class onSupprimerClicked(val taskList: TaskListWithTasksAndTagsSubTasks) : HomeScreenEvent()
    data class onSearch(val query: String) : HomeScreenEvent()
    data class onSearchFocusChanged(val focusState: FocusState) : HomeScreenEvent()
    object onTagRevealClicked : HomeScreenEvent()
}