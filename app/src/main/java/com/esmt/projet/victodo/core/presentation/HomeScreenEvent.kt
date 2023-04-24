package com.esmt.projet.victodo.core.presentation

sealed class HomeScreenEvent {
    data class onEditClicked(val id: Int) : HomeScreenEvent()
    data class onSupprimerClicked(val id: Int) : HomeScreenEvent()
    data class onSearch(val query: String) : HomeScreenEvent()
    object onTagRevealClicked : HomeScreenEvent()
}