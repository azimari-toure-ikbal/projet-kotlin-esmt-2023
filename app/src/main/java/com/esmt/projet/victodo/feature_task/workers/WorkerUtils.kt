package com.esmt.projet.victodo.feature_task.workers

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import com.esmt.projet.victodo.R
import com.esmt.projet.victodo.feature_task.domain.model.Task
import kotlin.random.Random

class WorkerUtils {
    fun showTaskNotification(context: Context, channelId: String, taskName: String){
        val notification = NotificationCompat.Builder(context, channelId)
            .setContentTitle("Due Task")
            .setContentText(taskName)
            .setSmallIcon(R.drawable.list_icon10_24px) // TODO: Change this icon
            .setPriority(NotificationCompat.PRIORITY_HIGH)
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