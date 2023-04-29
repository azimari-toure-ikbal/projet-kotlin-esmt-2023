package com.esmt.projet.victodo.feature_task.domain.use_case

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.work.*
import com.esmt.projet.victodo.exception.task.InvalidTaskException
import com.esmt.projet.victodo.feature_task.domain.model.TaskWithTagAndSubTask
import com.esmt.projet.victodo.feature_task.domain.repository.TaskRepository
import com.esmt.projet.victodo.feature_task.workers.TaskNotifierWorker
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.concurrent.TimeUnit

class AddTaskUseCase (
    val repository: TaskRepository
) {
    @RequiresApi(Build.VERSION_CODES.O)
    @Throws(InvalidTaskException::class)
    suspend operator fun invoke(
        taskWithTagAndSubTask: TaskWithTagAndSubTask,
        context: Context
    ): Long{
        if(taskWithTagAndSubTask.task.listId == null)
            throw InvalidTaskException("Task must be associated with a list")
        if(taskWithTagAndSubTask.task.name.isBlank())
            throw InvalidTaskException("Task must have a title")
        taskWithTagAndSubTask.tags.let {tags->
            for(tag in tags) {
                if(tag.id == null)
                    throw InvalidTaskException("Tag must have an id")
            }
        }
        taskWithTagAndSubTask.subtasks.let{subtasks->
            for(subTask in subtasks) {
                if(subTask.name.isBlank())
                    throw InvalidTaskException("SubTask must have a title")
            }
        }
        if(taskWithTagAndSubTask.task.dueDate != null && taskWithTagAndSubTask.task.dueTime == null)
            throw InvalidTaskException("Task must have a due time")
        if(taskWithTagAndSubTask.task.dueDate == null && taskWithTagAndSubTask.task.dueTime != null)
            throw InvalidTaskException("Task must have a due date")
        if(taskWithTagAndSubTask.task.dueDate != null && taskWithTagAndSubTask.task.dueTime != null && (taskWithTagAndSubTask.task.id == null || taskWithTagAndSubTask.task.id <= 0)) {
            if(taskWithTagAndSubTask.task.dueDate.isBefore(LocalDateTime.now().toLocalDate()))
                throw InvalidTaskException("Task due date must be in the future")
            if(taskWithTagAndSubTask.task.dueDate.isEqual(LocalDateTime.now().toLocalDate()) && taskWithTagAndSubTask.task.dueTime.isBefore(LocalDateTime.now().toLocalTime()))
                throw InvalidTaskException("Task due time must be in the future")
        }
        val id = repository.insertTask(taskWithTagAndSubTask)
        val dueDateTime = LocalDateTime.of(taskWithTagAndSubTask.task.dueDate, taskWithTagAndSubTask.task.dueTime).atZone(
            ZoneId.systemDefault()).toInstant().toEpochMilli()
        val inputData = workDataOf("task" to taskWithTagAndSubTask.task.name)
        val notifierWorkerRequest = OneTimeWorkRequestBuilder<TaskNotifierWorker>()
            .setInitialDelay(dueDateTime-System.currentTimeMillis(), TimeUnit.MILLISECONDS)
            .setInputData(inputData)
            .build()

        val workManager = WorkManager.getInstance(context)
        workManager.beginUniqueWork(
            taskWithTagAndSubTask.task.name+id,
            ExistingWorkPolicy.REPLACE,
            notifierWorkerRequest
        ).enqueue()

        return id
    }
}