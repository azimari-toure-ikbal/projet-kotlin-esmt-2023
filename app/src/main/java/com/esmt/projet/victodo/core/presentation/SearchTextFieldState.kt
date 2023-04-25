package com.esmt.projet.victodo.core.presentation

data class SearchTextFieldState(
    val searchQuery: String = "",
    val hint: String = "Search",
    val isHintVisible: Boolean = true,
)