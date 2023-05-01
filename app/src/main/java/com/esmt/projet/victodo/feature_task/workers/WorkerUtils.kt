package com.esmt.projet.victodo.feature_task.workers

import android.Manifest
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import com.esmt.projet.victodo.MainActivity
import com.esmt.projet.victodo.R
import com.esmt.projet.victodo.core.presentation.util.Screen
import com.google.accompanist.pager.ExperimentalPagerApi
import kotlin.random.Random

class WorkerUtils {
    @OptIn(ExperimentalAnimationApi::class, ExperimentalPagerApi::class)
    fun showTaskNotification(context: Context, channelId: String, taskName: String){
        val intent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            putExtra("screen", Screen.HomeScreen.route)
        }
        val pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        val notification = NotificationCompat.Builder(context, channelId)
            .setContentTitle("Due Task")
            .setContentText(taskName)
            .setSmallIcon(R.drawable.logo_36px)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .build()

        val notificationManager = NotificationManagerCompat.from(context)
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                return
            }
        }
        notificationManager.notify(
            Random.nextInt(),
            notification
        )
    }
}