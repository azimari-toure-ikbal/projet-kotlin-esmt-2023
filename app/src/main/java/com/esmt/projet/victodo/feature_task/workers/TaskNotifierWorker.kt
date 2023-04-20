package com.esmt.projet.victodo.feature_task.workers

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.esmt.projet.victodo.feature_task.domain.model.Task
import com.esmt.projet.victodo.feature_task.util.TASK_NOTIFICATION_CHANNEL_ID
import dagger.hilt.android.qualifiers.ApplicationContext

class TaskNotifierWorker(
    @ApplicationContext private val context: Context,
    private val workerParams: WorkerParameters
): Worker(context, workerParams) {
    override fun doWork(): Result {
        val task = workerParams.inputData.keyValueMap["task"] as Task
        WorkerUtils().showTaskNotification(
            context,
            TASK_NOTIFICATION_CHANNEL_ID,
            task)
        return Result.success()
    }
}