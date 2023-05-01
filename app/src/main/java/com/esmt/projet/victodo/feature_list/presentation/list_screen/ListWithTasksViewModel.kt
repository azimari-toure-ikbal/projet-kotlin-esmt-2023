package com.esmt.projet.victodo.feature_list.presentation.list_screen

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.esmt.projet.victodo.feature_list.util.ALL_TASKS_LIST
import com.esmt.projet.victodo.feature_list.util.COMPLETED_TASKS_LIST
import com.esmt.projet.victodo.feature_list.util.LATE_TASKS_LIST
import com.esmt.projet.victodo.feature_list.util.SCHEDULED_TASKS_LIST
import com.esmt.projet.victodo.feature_task.domain.model.Task
import com.esmt.projet.victodo.feature_task.domain.use_case.TaskUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListWithTasksViewModel @Inject constructor(
    private val taskuseCases: TaskUseCases,
    savedStateHandle: SavedStateHandle,
    @ApplicationContext private val context: Context
): ViewModel() {

    private val _state = mutableStateOf(ListWithTasksState())
    val state : State<ListWithTasksState> = _state
    
    private var currentListId : Long = ALL_TASKS_LIST.id!!

    private var taskListJob: Job? = null

    init {
        savedStateHandle.get<Long>("listId")?.let { listId ->
            currentListId = listId
        }
        getTasks()
    }

    @RequiresApi(Build.VERSION_CODES.O)
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
                    val task = event.taskWithTagAndSubTask.copy(
                        task = event.taskWithTagAndSubTask.task.copy(
                            isEnded = !event.taskWithTagAndSubTask.task.isEnded
                        )
                    )
                    taskuseCases.addTaskUseCase(task, context)
                    if(task.task.redundancy != Task.Companion.RepeatFrequency.NEVER.value){
                        when(task.task.redundancy){
                            Task.Companion.RepeatFrequency.DAILY.value -> {
                                taskuseCases.addTaskUseCase(task.copy(
                                    task = task.task.copy(
                                        id=null,
                                        dueDate = task.task.dueDate?.plusDays(1),
                                        isEnded = false
                                    )
                                ), context)
                            }
                            Task.Companion.RepeatFrequency.WEEKLY.value -> {
                                taskuseCases.addTaskUseCase(task.copy(
                                    task = task.task.copy(
                                        id=null,
                                        dueDate = task.task.dueDate?.plusWeeks(1),
                                        isEnded = false
                                    )
                                ), context)
                            }
                            Task.Companion.RepeatFrequency.MONTHLY.value -> {
                                taskuseCases.addTaskUseCase(task.copy(
                                    task = task.task.copy(
                                        id=null,
                                        dueDate = task.task.dueDate?.plusMonths(1),
                                        isEnded = false
                                    )
                                ), context)
                            }
                            Task.Companion.RepeatFrequency.YEARLY.value -> {
                                taskuseCases.addTaskUseCase(task.copy(
                                    task = task.task.copy(
                                        id=null,
                                        dueDate = task.task.dueDate?.plusYears(1),
                                        isEnded = false
                                    )
                                ), context) // TODO: à tester
                            }
                        }
                    }
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
                        listOfCompletedTask = it.filter { task -> task.task.isEnded },
                        listOfNonCompletedTask = it.filter { task -> !task.task.isEnded },
                        isTaskLoading = false
                    )
                }.launchIn(viewModelScope)
            }
            SCHEDULED_TASKS_LIST.id -> {
                taskListJob = taskuseCases.getScheduledTasksUseCase().onEach {
                    _state.value = ListWithTasksState(
                        listOfTasks = it,
                        listOfCompletedTask = it.filter { task -> task.task.isEnded },
                        listOfNonCompletedTask = it.filter { task -> !task.task.isEnded },
                        isTaskLoading = false
                    )
                }.launchIn(viewModelScope)
            }
            LATE_TASKS_LIST.id -> {
                taskListJob = taskuseCases.getLateTasksUseCase().onEach {
                    _state.value = ListWithTasksState(
                        listOfTasks = it,
                        listOfCompletedTask = it.filter { task -> task.task.isEnded },
                        listOfNonCompletedTask = it.filter { task -> !task.task.isEnded },
                        isTaskLoading = false
                    )
                }.launchIn(viewModelScope)
            }
            COMPLETED_TASKS_LIST.id -> {
                taskListJob = taskuseCases.getCompletedTasksUseCase().onEach {
                    _state.value = ListWithTasksState(
                        listOfTasks = it,
                        listOfCompletedTask = it.filter { task -> task.task.isEnded },
                        listOfNonCompletedTask = it.filter { task -> !task.task.isEnded },
                        isTaskLoading = false
                    )
                }.launchIn(viewModelScope)
            }
            else -> {
                taskListJob = taskuseCases.getTasksByListIdUseCase(currentListId).onEach {
                    _state.value = ListWithTasksState(
                        listOfTasks = it,
                        listOfCompletedTask = it.filter { task -> task.task.isEnded },
                        listOfNonCompletedTask = it.filter { task -> !task.task.isEnded },
                        isTaskLoading = false
                    )
                }.launchIn(viewModelScope)
            }
        }
    }
}