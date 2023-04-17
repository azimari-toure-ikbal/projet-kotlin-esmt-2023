package com.esmt.projet.victodo.feature_list.presentation.add_edit_screen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.ViewModel
import com.esmt.projet.victodo.feature_list.domain.model.TaskList
import com.esmt.projet.victodo.feature_list.domain.use_case.TaskListUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AddEditViewModel @Inject constructor(
    private val listUseCases: TaskListUseCases
) : ViewModel() {

    private val _listTitle = mutableStateOf(AddEditListsState())
    val listTitle : State<AddEditListsState> = _listTitle
    
    private val _listColor = mutableStateOf<Int>(TaskList.listColors.random().toArgb())
    val listColor : State<Int> = _listColor

    fun onEvent(event: AddEditListsEvent) {
        when (event) {
            is AddEditListsEvent.EnteredTitle -> {

            }
            is AddEditListsEvent.CreateList -> {
                TODO()
            }
            is AddEditListsEvent.SelectedColor -> {
                TODO()
            }
            is AddEditListsEvent.SelectedIcon -> {
                TODO()
            }
        }
    }
}