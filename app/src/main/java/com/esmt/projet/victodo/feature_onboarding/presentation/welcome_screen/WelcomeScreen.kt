package com.esmt.projet.victodo.feature_onboarding.presentation.welcome_screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.esmt.projet.victodo.core.presentation.util.Screen
import com.esmt.projet.victodo.feature_onboarding.util.OnBoardingPage
import com.google.accompanist.pager.*
import kotlinx.coroutines.*

@OptIn(ExperimentalPagerApi::class)
@Composable
fun WelcomeScreen(
    welcomeViewModel: WelcomeViewModel = hiltViewModel(),
    navController: NavHostController
) {

    val pagerState = rememberPagerState()

    Column(modifier = Modifier.fillMaxSize()) {
        HorizontalPager(
            modifier = Modifier.weight(10f),
            count = 3,
            state = pagerState,
            verticalAlignment = Alignment.Top
        ) { position ->
            PagerScreen(onBoardingPage = welcomeViewModel.pages[position], navController = navController, welcomeViewModel = welcomeViewModel, pagerState = pagerState)
        }
        HorizontalPagerIndicator(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .weight(1f),
            pagerState = pagerState,
            inactiveColor = Color(0xFFEEF5FD),
            activeColor = Color(0xFF006EE9),
        )
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun PagerScreen(
    onBoardingPage: OnBoardingPage,
    navController: NavHostController,
    welcomeViewModel: WelcomeViewModel,
    pagerState: PagerState
) {
    Scaffold(
        topBar = {
                 Row(
                     modifier = Modifier.padding(16.dp),
                     horizontalArrangement = Arrangement.SpaceBetween
                 ) {
                    Text(
                        text = "",
                        modifier = Modifier.weight(0.5f)
                    )
                     Text(
                         text = "Skip",
                         color = Color(0xFF006EE9),
                         modifier = Modifier
                             .clickable {
                                 welcomeViewModel.saveOnBoardingState(true)
                                 navController.popBackStack()
                                 navController.navigate(Screen.HomeScreen.route)
                             }
                     )
                 }
        },
        bottomBar = {
            if (pagerState.currentPage == 2) {
                Button(
                    onClick = {
                        welcomeViewModel.saveOnBoardingState(true)
                        navController.popBackStack()
                        navController.navigate(Screen.HomeScreen.route)
                    },
                    modifier = Modifier
                        .padding(start = 28.dp, end = 28.dp)
                        .fillMaxWidth()
                        .padding(vertical = 48.dp),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color(0xFF006EE9),
                        contentColor = Color.White
                    )
                ) {
                    Text(
                        text = "Get Started",
                    )
                }
            }
        }
    ) { contentPadding ->
        Box(modifier = Modifier.padding(contentPadding)) {
            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                Image(
                    modifier = Modifier
                        .padding(start = 28.dp, end = 28.dp)
                        .fillMaxWidth(1f)
                        .fillMaxHeight(0.7f),
                    painter = painterResource(id = onBoardingPage.image),
                    contentDescription = "Pager Image"
                )
                Text(
                    modifier = Modifier
                        .padding(start = 28.dp, end = 28.dp)
                        .fillMaxWidth(),
                    text = onBoardingPage.title,
                    fontSize = MaterialTheme.typography.h5.fontSize,
                    fontWeight = FontWeight.SemiBold,
                    textAlign = TextAlign.Center
                )
                Text(
                    modifier = Modifier
                        .padding(start = 28.dp, end = 28.dp)
                        .fillMaxWidth()
                        .padding(horizontal = 40.dp)
                        .padding(top = 20.dp),
                    text = onBoardingPage.description,
                    fontSize = MaterialTheme.typography.subtitle1.fontSize,
                    fontWeight = FontWeight.Normal,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

//@Preview(showBackground = true)
//@Composable
//fun FirstScreenPreview() {
//    Column(modifier = Modifier.fillMaxSize()) {
//        PagerScreen(onBoardingPage = OnBoardingPage.FirstPage)
//    }
//}
//
//@Preview(showBackground = true)
//@Composable
//fun SecondScreenPreview() {
//    Column(modifier = Modifier.fillMaxSize()) {
//        PagerScreen(onBoardingPage = OnBoardingPage.SecondPage)
//    }
//}
//
//@Preview(showBackground = true)
//@Composable
//fun ThirdScreenPreview() {
//    Column(modifier = Modifier.fillMaxSize()) {
//        PagerScreen(onBoardingPage = OnBoardingPage.ThirdPage)
//    }
//}