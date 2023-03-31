package com.esmt.projet.victodo.feature_x.presentation.x_screen

import com.esmt.projet.victodo.feature_x.domain.model.Mockup

data class MockupState(
    val mockups: List<Mockup> = emptyList(),
    val isLoading: Boolean = false,
    val error: String = "",
)
