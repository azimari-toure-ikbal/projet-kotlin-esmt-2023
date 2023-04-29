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
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEditViewModel @Inject constructor(
    private val listUseCases: TaskListUseCases,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _listTitle = mutableStateOf("")
    val listTitle : State<String> = _listTitle
    
    private val _listColor = mutableStateOf(TaskList.listColors.random().toArgb())
    val listColor : State<Int> = _listColor

    private val _listIcon = mutableStateOf(TaskList.listIcons[0])
    val listIcon : State<Int> = _listIcon

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private var currentListId : Long? = null

    init {
        savedStateHandle.get<Long>("listId")?.let { listId ->
            if (listId != null && listId > 0) {
                viewModelScope.launch {
                    listUseCases.getTaskListUseCase(listId).let { taskList ->
                        currentListId = taskList.taskList.id
                        _listTitle.value = taskList.taskList.title
                        _listColor.value = taskList.taskList.color
                        _listIcon.value = taskList.taskList.icon
                    }
                }
            }
        }
    }

    fun onEvent(event: AddEditListsEvent) {
        when (event) {
            is AddEditListsEvent.EnteredTitle -> {
                _listTitle.value = event.title
                print(_listTitle.value)
            }
            is AddEditListsEvent.CreateList -> {
                print("Creating list...")
                viewModelScope.launch {
                    try {
                        listUseCases.addTaskListUseCase(
                            TaskList(
                                id = currentListId,
                                title = _listTitle.value,
                                color = _listColor.value,
                                isPinned = false,
                                icon = _listIcon.value,
                                isDefault = false
                            )
                        )
                        print("List created successfully")
                        _eventFlow.emit(UiEvent.NavigateBackWithResult(Result.OK.ordinal))
                    } catch (e: InvalidListException) {
                        print("Couldn't create list")
                        _eventFlow.emit(UiEvent.ShowInvalidInputMessage(e.message ?: "Couldn't create list"))
                    }
                }
            }
            is AddEditListsEvent.SelectedColor -> {
                _listColor.value = event.color
                print(_listColor.value)
            }
            is AddEditListsEvent.SelectedIcon -> {
                _listIcon.value = event.icon
                print(_listIcon.value)
            }
        }
    }
}

sealed class UiEvent {
    data class ShowInvalidInputMessage(val message: String) : UiEvent()
    data class NavigateBackWithResult(val result: Int) : UiEvent()
}

//private fun generateUUID(): Long {
//    return UUID.randomUUID().mostSignificantBits and Long.MAX_VALUE
//}

private enum class Result {
    OK, CANCELED
}