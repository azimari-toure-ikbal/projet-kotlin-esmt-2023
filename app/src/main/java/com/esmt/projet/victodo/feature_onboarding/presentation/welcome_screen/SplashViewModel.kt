package com.esmt.projet.victodo.feature_onboarding.presentation.welcome_screen

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.esmt.projet.victodo.core.presentation.util.Screen
import com.esmt.projet.victodo.feature_onboarding.data.repository.DataStoreRepository
import javax.inject.Inject

class SplashViewModel @Inject constructor(
    private val repository: DataStoreRepository
) : ViewModel() {

    private val _isLoading: MutableState<Boolean> = mutableStateOf(true)
    val isLoading: State<Boolean> = _isLoading

    private val _startDestination: MutableState<String> = mutableStateOf(Screen.OnBoardingScreen.route)
    val startDestination: State<String> = _startDestination

    fun setStartDestination(destination: String) {
        _startDestination.value = destination
    }

//    init {
//        viewModelScope.launch {
//            repository.readOnBoardingState().collect { completed ->
//                if (completed) {
//                    Log.d("HomeScreen", "init: $completed")
//                    _startDestination.value = Screen.HomeScreen.route
//                } else {
//                    Log.d("OnBoarding", "init: $completed")
//                    _startDestination.value = Screen.OnBoardingScreen.route
//                }
//            }
//            _isLoading.value = false
//        }
//    }

}
