package com.esmt.projet.victodo.feature_onboarding.presentation.welcome_screen

import androidx.lifecycle.ViewModel
import com.esmt.projet.victodo.feature_onboarding.data.repository.DataStoreRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class WelcomeViewModel @Inject constructor(
    private val repository: DataStoreRepository
) : ViewModel() {
    
}