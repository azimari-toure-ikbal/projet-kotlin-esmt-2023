package com.esmt.projet.victodo.core.presentation

import androidx.lifecycle.ViewModel
import com.esmt.projet.victodo.feature_list.domain.use_case.TaskListUseCases
import com.esmt.projet.victodo.feature_tag.domain.use_case.TagUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val listUseCases: TaskListUseCases,
//    private val tagUseCases: TagUseCases
) : ViewModel() {

}