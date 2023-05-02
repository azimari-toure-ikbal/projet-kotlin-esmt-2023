package com.esmt.projet.victodo.feature_task.workers

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.esmt.projet.victodo.feature_task.util.TASK_NOTIFICATION_CHANNEL_ID
import dagger.hilt.android.qualifiers.ApplicationContext
import java.time.LocalDateTime

class TaskNotifierWorker(
    @ApplicationContext private val context: Context,
    private val workerParams: WorkerParameters
): Worker(context, workerParams) {
    override fun doWork(): Result {
        Log.d("TaskNotifierWorker", "doWork: "+workerParams.inputData.keyValueMap["task"]+" "+LocalDateTime.now())
        val taskName = workerParams.inputData.keyValueMap["task"] as String
        WorkerUtils().showTaskNotification(
            context,
            TASK_NOTIFICATION_CHANNEL_ID,
            taskName)
        return Result.success()
    }
}