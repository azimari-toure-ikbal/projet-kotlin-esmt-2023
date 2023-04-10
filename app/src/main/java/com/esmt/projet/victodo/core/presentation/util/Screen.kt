package com.esmt.projet.victodo.core.presentation.util

sealed class Screen(val route: String) {
    object OnBoardingScreen : Screen("onBoarding_screen")
    object HomeScreen : Screen("home_screen")
}
