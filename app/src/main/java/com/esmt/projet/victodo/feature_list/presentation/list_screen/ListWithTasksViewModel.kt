package com.esmt.projet.victodo.feature_list.presentation.list_screen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.esmt.projet.victodo.feature_list.domain.use_case.TaskListUseCases
import com.esmt.projet.victodo.feature_list.util.ALL_TASKS_LIST
import com.esmt.projet.victodo.feature_list.util.COMPLETED_TASKS_LIST
import com.esmt.projet.victodo.feature_list.util.LATE_TASKS_LIST
import com.esmt.projet.victodo.feature_list.util.SCHEDULED_TASKS_LIST
import com.esmt.projet.victodo.feature_task.domain.use_case.TaskUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import okhttp3.internal.wait
import javax.inject.Inject

@HiltViewModel
class ListWithTasksViewModel @Inject constructor(
    private val listUseCases: TaskListUseCases,
    private val taskuseCases: TaskUseCases,
    savedStateHandle: SavedStateHandle
): ViewModel() {

    private val _state = mutableStateOf(ListWithTasksState())
    val state : State<ListWithTasksState> = _state
    
    private var currentListId : Long = 0

    private var taskListJob: Job? = null

    init {
        savedStateHandle.get<Long>("listId").let { listId ->
            currentListId = if (listId == 0L) {
                ALL_TASKS_LIST.id!!
            } else {
                listId!!
            }
            getTasks()
        }
    }

    fun onEvent(event: ListWithTasksEvent){
        when(event) {
            is ListWithTasksEvent.OnDeleteClick -> {
                viewModelScope.launch {
                    val task = event.taskWithTagAndSubTask.task
                    _state.value = with(state.value){
                        copy(
                            listOfTasks = listOfTasks.filter { it.task.id != task.id }
                        )
                    }
                    taskuseCases.deleteTaskUseCase(event.taskWithTagAndSubTask)
                }
            }
            is ListWithTasksEvent.OnCompletedClick -> {
                viewModelScope.launch {
                    // TODO()
                }
            }
        }
    }

    private fun getTasks(){
        taskListJob?.cancel()
        when(currentListId){
            ALL_TASKS_LIST.id -> {
                taskListJob = taskuseCases.getTasksUseCase().onEach {
                    _state.value = ListWithTasksState(
                        listOfTasks = it,
                        isTaskLoading = false
                    )
                }.launchIn(viewModelScope)
            }
            SCHEDULED_TASKS_LIST.id -> {
                taskListJob = taskuseCases.getScheduledTasksUseCase().onEach {
                    _state.value = ListWithTasksState(
                        listOfTasks = it,
                        isTaskLoading = false
                    )
                }.launchIn(viewModelScope)
            }
            LATE_TASKS_LIST.id -> {
                taskListJob = taskuseCases.getLateTasksUseCase().onEach {
                    _state.value = ListWithTasksState(
                        listOfTasks = it,
                        isTaskLoading = false
                    )
                }.launchIn(viewModelScope)
            }
            COMPLETED_TASKS_LIST.id -> {
                taskListJob = taskuseCases.getCompletedTasksUseCase().onEach {
                    _state.value = ListWithTasksState(
                        listOfTasks = it,
                        isTaskLoading = false
                    )
                }.launchIn(viewModelScope)
            }
            else -> {
                viewModelScope.launch {
                    val taskListWithTasksAndTagsSubTasks = listUseCases.getTaskListUseCase(currentListId)
                    _state.value = ListWithTasksState(
                        listOfTasks = taskListWithTasksAndTagsSubTasks.tasks,
                        isTaskLoading = false
                    )
                }
            }
        }
    }
}