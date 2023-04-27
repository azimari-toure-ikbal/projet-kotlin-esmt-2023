package com.esmt.projet.victodo.core.presentation.util

sealed class Screen(val route: String) {
    object OnBoardingScreen : Screen("onBoarding_screen")
    object HomeScreen : Screen("home_screen")
    object AddEditListScreen : Screen("add_edit_list_screen")
    object AddEditTaskScreen : Screen("add_edit_task_screen")
    object ListWithTasksScreen : Screen("list_with_tasks_screen")

}
