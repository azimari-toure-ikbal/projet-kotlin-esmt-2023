package com.esmt.projet.victodo.feature_x.presentation.x_screen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.esmt.projet.victodo.feature_x.domain.use_case.MockupUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class MockupViewModel @Inject constructor(
    private val mockupUseCases: MockupUseCases
): ViewModel() {

    private val _state = mutableStateOf(MockupState())
    val state: State<MockupState> = _state

    fun onEvent(event: MockupEvent) {
        when(event) {
            is MockupEvent.GetAll -> {

            }
        }
    }
}