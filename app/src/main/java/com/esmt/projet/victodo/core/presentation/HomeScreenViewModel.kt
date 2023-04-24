package com.esmt.projet.victodo.core.presentation

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.esmt.projet.victodo.feature_list.domain.model.TaskList
import com.esmt.projet.victodo.feature_list.domain.use_case.TaskListUseCases
import com.esmt.projet.victodo.feature_tag.domain.model.Tag
import com.esmt.projet.victodo.feature_tag.domain.use_case.TagUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val listUseCases: TaskListUseCases,
    private val tagUseCases: TagUseCases,
) : ViewModel() {
    
    private val _searchQuery = mutableStateOf(HomeScreenState(
        searchQuery = ""
    ))
    val searchQuery : State<HomeScreenState> = _searchQuery

    private val _isTagRevealed = mutableStateOf(HomeScreenState(
        isTagRevealed = false
    ))
    val isTagRevealed : State<HomeScreenState> = _isTagRevealed

    private val _listOfPinnedList = mutableStateOf(HomeScreenState(
        listOfPinnedList = emptyList()
    ))
    val listOfPinnedList : State<HomeScreenState> = _listOfPinnedList

    private val _listOfTaskList = mutableStateOf(HomeScreenState(
        listOfTaskList = emptyList()
    ))
    val listOfTaskList : State<HomeScreenState> = _listOfTaskList

    private val _listOfTags = mutableStateOf(HomeScreenState(
        listOfTags = emptyList()
    ))
    val listOfTags : State<HomeScreenState> = _listOfTags

}