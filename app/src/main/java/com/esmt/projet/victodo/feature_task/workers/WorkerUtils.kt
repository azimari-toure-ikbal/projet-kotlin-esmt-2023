package com.esmt.projet.victodo.feature_task.workers

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import com.esmt.projet.victodo.R
import com.esmt.projet.victodo.feature_task.domain.model.Task
import kotlin.random.Random

class WorkerUtils {
    fun showTaskNotification(context: Context, channelId: String, task: Task){
        val notification = NotificationCompat.Builder(context, channelId)
            .setContentTitle("Due Task")
            .setContentText(task.name)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .build()

        val notificationManager = NotificationManagerCompat.from(context)
        if (ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }
        notificationManager.notify(
            Random.nextInt(),
            notification
        )
    }
}