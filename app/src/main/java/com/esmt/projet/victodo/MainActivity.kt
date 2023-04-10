package com.esmt.projet.victodo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.esmt.projet.victodo.core.presentation.util.Screen
import com.esmt.projet.victodo.feature_onboarding.presentation.welcome_screen.SplashViewModel
import com.esmt.projet.victodo.feature_onboarding.presentation.welcome_screen.WelcomeScreen
import com.esmt.projet.victodo.ui.theme.VictoDoTheme
import javax.inject.Inject

class MainActivity : ComponentActivity() {

//    @Inject
//    lateinit var splashViewModel: SplashViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        installSplashScreen().setKeepOnScreenCondition {
//            !splashViewModel.isLoading.value
//        }

        setContent {
            VictoDoTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
//                    val screen by splashViewModel.startDestination
                    val navController = rememberNavController()

                    NavHost(
                        navController = navController,
                        startDestination = Screen.OnBoardingScreen.route
                    ) {
                        composable(route = Screen.OnBoardingScreen.route) {
                            WelcomeScreen(navController = navController)
                        }
                        composable(route = Screen.HomeScreen.route) {

                        }
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    VictoDoTheme {
        Greeting("Android")
    }
}