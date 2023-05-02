package com.esmt.projet.victodo

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.esmt.projet.victodo.core.presentation.HomeScreen
import com.esmt.projet.victodo.core.presentation.util.Screen
import com.esmt.projet.victodo.feature_list.domain.model.TaskList
import com.esmt.projet.victodo.feature_list.presentation.add_edit_screen.AddEditListScreen
import com.esmt.projet.victodo.feature_list.presentation.list_screen.ListWithTasksScreen
import com.esmt.projet.victodo.feature_list.util.ALL_TASKS_LIST
import com.esmt.projet.victodo.feature_onboarding.presentation.welcome_screen.SplashViewModel
import com.esmt.projet.victodo.feature_onboarding.presentation.welcome_screen.WelcomeScreen
import com.esmt.projet.victodo.feature_task.presentation.add_edit_screen.AddEditTaskScreen
import com.esmt.projet.victodo.ui.theme.VictoDoTheme
import com.google.accompanist.pager.ExperimentalPagerApi
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@ExperimentalAnimationApi
@ExperimentalPagerApi
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var splashViewModel: SplashViewModel

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (application as VictoDoApp).createNotificationChannel()
//        installSplashScreen().setKeepOnScreenCondition {
//            !splashViewModel.isLoading.value
//        }

        val preferences = getSharedPreferences("onBoarding", MODE_PRIVATE)
        val isFirstTime = preferences.getBoolean("isFirstTime", true)

        if (isFirstTime) {
            with(preferences.edit()) {
                putBoolean("isFirstTime", false)
                apply()
            }
            splashViewModel.setStartDestination(Screen.OnBoardingScreen.route)
        } else {
            splashViewModel.setStartDestination(Screen.HomeScreen.route)
        }

//        CoroutineScope(Dispatchers.Main).launch {
//            installSplashScreen()
//        }
        setContent {
            VictoDoTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val screen by splashViewModel.startDestination
                    Log.d("MainActivity", "onCreate: $screen")
                    val loading by splashViewModel.isLoading
                    val navController = rememberNavController()

                    NavHost(
                        navController = navController,
                        startDestination = screen
                    ) {
                        composable(route = Screen.HomeScreen.route) {
                            HomeScreen(navController = navController)
                        }
                        composable(route = Screen.OnBoardingScreen.route) {
                            WelcomeScreen(navController = navController)
                        }
                        composable(
                            route = Screen.AddEditListScreen.route +
                                    "?listId={listId}&listColor={listColor}&listTitle={listTitle}",
                            arguments = listOf(
                                navArgument(
                                    name = "listId"
                                ) {
                                   type = NavType.LongType
                                    defaultValue = -1L
                                },
                                navArgument(
                                    name = "listColor"
                                ) {
                                    type = NavType.IntType
                                    defaultValue = -1
                                },
                                navArgument(
                                    name = "listTitle"
                                ) {
                                    type = NavType.StringType
                                    defaultValue = ""
                                }
                            )
                        ) {
                            val color = it.arguments?.getInt("listColor") ?: -1
                            AddEditListScreen(
                                navController = navController,
                                listColor = color
                            )
                        }
                        composable(
                            route = Screen.ListWithTasksScreen.route+
                                    "/{listId}/{listTitle}/{listIcon}",
                            arguments = listOf(
                                navArgument(
                                    name = "listId"
                                ) {
                                    type = NavType.LongType
                                },
                                navArgument(
                                    name = "listTitle"
                                ) {
                                    type = NavType.StringType
                                },
                                navArgument(
                                    name = "listIcon"
                                ) {
                                    type = NavType.IntType
                                }
                            )
                        ){
                            ListWithTasksScreen(
                                navController = navController,
                                taskList = TaskList(
                                    id = it.arguments?.getLong("listId") ?: ALL_TASKS_LIST.id,
                                    title = it.arguments?.getString("listTitle") ?: ALL_TASKS_LIST.title,
                                    icon = it.arguments?.getInt("listIcon") ?: ALL_TASKS_LIST.icon
                                )
                            )
                        }

                        composable(
                            route = Screen.AddEditTaskScreen.route +
                                    "?taskId={taskId}",
                            arguments = listOf(
                                navArgument(
                                    name = "taskId"
                                ) {
                                    type = NavType.LongType
                                    defaultValue = 0L
                                }
                            )
                        ){
                            AddEditTaskScreen(navController = navController)
                            /*
                                navController = navController,
                                taskId = it.arguments?.getLong("taskId") ?: 0L
                             */
                        }
                    }
                }
            }
        }
    }
}