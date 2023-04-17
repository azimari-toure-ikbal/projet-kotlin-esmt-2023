package com.esmt.projet.victodo.feature_list.presentation.add_edit_screen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.esmt.projet.victodo.exception.list.InvalidListException
import com.esmt.projet.victodo.feature_list.domain.model.TaskList
import com.esmt.projet.victodo.feature_list.domain.use_case.TaskListUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class AddEditViewModel @Inject constructor(
    private val listUseCases: TaskListUseCases
) : ViewModel() {

    private val _listTitle = mutableStateOf(AddEditListsState(
        listTitle = ""
    ))
    val listTitle : State<AddEditListsState> = _listTitle
    
    private val _listColor = mutableStateOf<Int>(TaskList.listColors.random().toArgb())
    val listColor : State<Int> = _listColor

    private val _listIcon = mutableStateOf<String>(TaskList.listIcons.random().toString())
    val listIcon : State<String> = _listIcon

    fun onEvent(event: AddEditListsEvent) {
        when (event) {
            is AddEditListsEvent.EnteredTitle -> {
                _listTitle.value = listTitle.value.copy(listTitle = event.title)
            }
            is AddEditListsEvent.CreateList -> {
                viewModelScope.launch {
                    try {
                        listUseCases.addTaskListUseCase(
                            TaskList(
                                id = generateUUID(),
                                title = event.taskList.title,
                                color = event.taskList.color,
                                isPinned = false,
                                icon = event.taskList.icon,
                                isDefault = false
                            )
                        )
                    } catch (e: InvalidListException) {

                    }
                }
            }
            is AddEditListsEvent.SelectedColor -> {
                _listColor.value = event.color
            }
            is AddEditListsEvent.SelectedIcon -> {
                _listIcon.value = event.icon
            }
        }
    }
}

private fun generateUUID(): Long {
    return UUID.randomUUID().mostSignificantBits and Long.MAX_VALUE
}