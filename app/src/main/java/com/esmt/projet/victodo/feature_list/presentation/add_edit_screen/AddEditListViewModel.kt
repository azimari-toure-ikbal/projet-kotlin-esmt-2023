package com.esmt.projet.victodo.feature_list.presentation.add_edit_screen

import androidx.lifecycle.ViewModel
import com.esmt.projet.victodo.feature_list.domain.use_case.TaskListUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AddEditViewModel @Inject constructor(
    private val listUseCases: TaskListUseCases
) : ViewModel() {

}