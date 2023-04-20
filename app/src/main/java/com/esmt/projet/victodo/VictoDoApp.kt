package com.esmt.projet.victodo

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import com.esmt.projet.victodo.feature_task.util.TASK_NOTIFICATION_CHANNEL_ID
import com.esmt.projet.victodo.feature_task.util.TASK_NOTIFICATION_CHANNEL_NAME
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class VictoDoApp: Application(){
    fun createNotificationChannel(){
        if(Build.VERSION.SDK_INT>= Build.VERSION_CODES.O){
            val channel = NotificationChannel(
                TASK_NOTIFICATION_CHANNEL_ID,
                TASK_NOTIFICATION_CHANNEL_NAME,
                NotificationManager.IMPORTANCE_HIGH)
            val notificationManager = getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(channel)
        }
    }
}