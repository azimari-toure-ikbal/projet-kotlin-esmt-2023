package com.esmt.projet.victodo.feature_list.presentation.add_edit_screen

import com.esmt.projet.victodo.feature_list.domain.model.TaskList

sealed class AddEditListsEvent {
    data class EnteredTitle(val title: String) : AddEditListsEvent()
    data class SelectedColor(val color: Int) : AddEditListsEvent()
    data class SelectedIcon(val icon: String) : AddEditListsEvent()
    data class CreateList(val taskList: TaskList) : AddEditListsEvent()
}
