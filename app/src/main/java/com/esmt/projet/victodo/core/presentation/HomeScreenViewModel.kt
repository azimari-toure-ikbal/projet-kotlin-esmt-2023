package com.esmt.projet.victodo.core.presentation

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.esmt.projet.victodo.feature_list.domain.model.TaskListWithTasksAndTagsSubTasks
import com.esmt.projet.victodo.feature_list.domain.use_case.TaskListUseCases
import com.esmt.projet.victodo.feature_tag.domain.use_case.TagUseCases
import com.esmt.projet.victodo.feature_task.domain.use_case.TaskUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val listUseCases: TaskListUseCases,
    private val tagUseCases: TagUseCases,
    private val taskUseCases: TaskUseCases
) : ViewModel() {

    private var listOfTaskList: List<TaskListWithTasksAndTagsSubTasks> = emptyList()
    private val _state = mutableStateOf(HomeScreenState())
    val state : State<HomeScreenState> = _state

    private val _searchFieldState = mutableStateOf(SearchTextFieldState())
    val searchFieldState : State<SearchTextFieldState> = _searchFieldState

    private var getPinnedListJob: Job? = null
    private var getTaskListJob: Job? = null
    private var getTagListJob: Job? = null

    init {
        getPinnedList()
        getTaskList()
        getTagList()
    }
    fun onEvent(event: HomeScreenEvent){
        when(event){
            is HomeScreenEvent.OnSearch -> {
                if(event.query.isNotBlank() || event.query.isEmpty()){
                    _searchFieldState.value = _searchFieldState.value.copy(
                        searchQuery = event.query
                    )
                    _state.value = _state.value.copy(
                        listOfTaskList = listOfTaskList.filter {
                            it.taskList.title.contains(event.query, ignoreCase = true)
                        }
                    )
                }
            }
            is HomeScreenEvent.OnSupprimerClicked -> {
                viewModelScope.launch {
                    listUseCases.deleteTaskListUseCase(event.taskList)
                }
            }
            is HomeScreenEvent.OnTagRevealClicked ->{
                _state.value = _state.value.copy(
                    isTagRevealed = !_state.value.isTagRevealed
                )
            }
            is HomeScreenEvent.OnSearchFocusChanged -> {
                _searchFieldState.value = _searchFieldState.value.copy(
                    isHintVisible = !event.focusState.isFocused &&
                            _searchFieldState.value.searchQuery.isBlank()
                )
            }
        }
    }

    private fun getPinnedList(){
        getPinnedListJob?.cancel()
        getPinnedListJob = listUseCases.getUtilTaskListsUseCase().onEach {
            _state.value = _state.value.copy(
                listOfPinnedList = it
            )
        }.launchIn(viewModelScope)
    }

    private fun getTaskList(){
        getTaskListJob?.cancel()
        getTaskListJob = listUseCases.getTaskListsUseCase().onEach {
            listOfTaskList = it
            _state.value = _state.value.copy(
                listOfTaskList = it
            )
        }.launchIn(viewModelScope)

//        taskUseCases.getLateTasksUseCase().onEach {
//            Log.d("HomeScreenViewModel", "getTaskList: ${it.size}")
//        }.launchIn(viewModelScope)
    }

    private fun getTagList(){
        getTagListJob?.cancel()
        getTagListJob = tagUseCases.getAllTagUseCase().onEach {
            _state.value = _state.value.copy(
                listOfTags = it
            )
        }.launchIn(viewModelScope)
    }
}