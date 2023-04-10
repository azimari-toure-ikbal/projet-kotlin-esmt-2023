package com.esmt.projet.victodo.feature_onboarding.util

import androidx.annotation.DrawableRes
import com.esmt.projet.victodo.R

sealed class OnBoardingPage(
    @DrawableRes
    val image: Int,
    val title: String,
    val description: String
) {
    object FirstPage : OnBoardingPage(
        image = R.drawable.welcome_management,
        title = "Easy Time Management",
        description = "With management based on priority and daily tasks, it will give you convenience in managing and determining the tasks that must be done first"
    )

    object SecondPage : OnBoardingPage(
        image = R.drawable.welcome_chart,
        title = "Increase Work Effectiveness",
        description = "Time management and the determination of more important tasks will give your job statistics better and always improve"
    )

    object ThirdPage : OnBoardingPage(
        image = R.drawable.welcome_notification,
        title = "Reminder Notification",
        description = "The advantage of this application is that it also provides reminders for you so you don't forget to keep doing your assignments well and according to the time you have set"
    )

}
