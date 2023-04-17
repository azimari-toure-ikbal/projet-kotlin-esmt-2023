package com.esmt.projet.victodo.feature_list.presentation.add_edit_screen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.esmt.projet.victodo.exception.list.InvalidListException
import com.esmt.projet.victodo.feature_list.domain.model.TaskList
import com.esmt.projet.victodo.feature_list.domain.use_case.TaskListUseCases
import com.esmt.projet.victodo.feature_list.presentation.add_edit_screen.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class AddEditViewModel @Inject constructor(
    private val listUseCases: TaskListUseCases,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _listTitle = mutableStateOf(AddEditListsState(
        listTitle = ""
    ))
    val listTitle : State<AddEditListsState> = _listTitle
    
    private val _listColor = mutableStateOf<Int>(TaskList.listColors.random().toArgb())
    val listColor : State<Int> = _listColor

    private val _listIcon = mutableStateOf<String>(TaskList.listIcons.random().toString())
    val listIcon : State<String> = _listIcon

    private val _eventFlow = MutableSharedFlow<UiEvent>()

    private var currentListId : Long? = null

    init {
        savedStateHandle.get<Long>("listId")?.let { listId ->
            if (listId != -1L) {
                viewModelScope.launch {
                    
                }
            }
        }
    }

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
                                id = currentListId ?: generateUUID(),
                                title = event.taskList.title,
                                color = event.taskList.color,
                                isPinned = false,
                                icon = event.taskList.icon,
                                isDefault = false
                            )
                        )
                        _eventFlow.emit(UiEvent.NavigateBackWithResult(Result.OK.ordinal))
                    } catch (e: InvalidListException) {
                        _eventFlow.emit(UiEvent.ShowInvalidInputMessage(e.message ?: "Couldn't create list"))
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

sealed class UiEvent {
    data class ShowInvalidInputMessage(val message: String) : UiEvent()
    data class NavigateBackWithResult(val result: Int) : UiEvent()
}

private fun generateUUID(): Long {
    return UUID.randomUUID().mostSignificantBits and Long.MAX_VALUE
}

private enum class Result {
    OK, CANCELED
}