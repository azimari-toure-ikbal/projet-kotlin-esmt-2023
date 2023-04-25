package com.esmt.projet.victodo.core.presentation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.esmt.projet.victodo.feature_list.domain.use_case.TaskListUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val listUseCases: TaskListUseCases
) : ViewModel() {

    private val _state = mutableStateOf(HomeScreenState())
    val state : State<HomeScreenState> = _state

    private val _searchFieldState = mutableStateOf(SearchTextFieldState())
    val searchFieldState : State<SearchTextFieldState> = _searchFieldState

    private var getPinnedListJob: Job? = null
    private var getTaskListJob: Job? = null

    init {
        getPinnedList()
        getTaskList()
    }
    fun onEvent(event: HomeScreenEvent){
        when(event){
            is HomeScreenEvent.onEditClicked -> {
                // TODO: 12/07/2021
            }
            is HomeScreenEvent.onSearch -> {
                _searchFieldState.value = _searchFieldState.value.copy(
                    searchQuery = event.query
                )
            }
            is HomeScreenEvent.onSupprimerClicked -> {
                viewModelScope.launch {
                    listUseCases.deleteTaskListUseCase(event.taskList)
                }
            }
            is HomeScreenEvent.onTagRevealClicked ->{
                _state.value = _state.value.copy(
                    isTagRevealed = !_state.value.isTagRevealed
                )
            }
            is HomeScreenEvent.onSearchFocusChanged -> {
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
            _state.value = _state.value.copy(
                listOfTaskList = it
            )
        }.launchIn(viewModelScope)
    }
}