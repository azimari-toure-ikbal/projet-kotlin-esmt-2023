package com.esmt.projet.victodo.feature_task.domain.use_case

import android.content.Context
import android.os.Build
import android.util.Log
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

        val taskWithTagAndSubTaskToAdd = taskWithTagAndSubTask.copy(
            task = taskWithTagAndSubTask.task.copy(
                timestamp = System.currentTimeMillis(),
            )
        )
        Log.i("AddTaskUseCase", "taskWithTagAndSubTaskToAdd = $taskWithTagAndSubTaskToAdd")
        Log.i("AddTaskUseCase", "taskWithTagAndSubTask = $taskWithTagAndSubTask")

        Log.d("AddTaskUseCase", "entrée dans invoke")
        if(taskWithTagAndSubTaskToAdd.task.listId == null || taskWithTagAndSubTaskToAdd.task.listId <= 0L)
            throw InvalidTaskException("Task must be associated with a list")
        if(taskWithTagAndSubTaskToAdd.task.name.isBlank())
            throw InvalidTaskException("Task must have a title")
        taskWithTagAndSubTaskToAdd.tags.let {tags->
            for(tag in tags) {
                if(tag.id == null)
                    throw InvalidTaskException("Tag must have an id")
            }
        }
        taskWithTagAndSubTaskToAdd.subtasks.let{subtasks->
            for(subTask in subtasks) {
                if(subTask.name.isBlank())
                    throw InvalidTaskException("SubTask must have a title")
            }
        }

        if(taskWithTagAndSubTaskToAdd.task.dueDate != null && taskWithTagAndSubTaskToAdd.task.dueTime == null)
            throw InvalidTaskException("Task must have a due time")
        if(taskWithTagAndSubTaskToAdd.task.dueDate == null && taskWithTagAndSubTaskToAdd.task.dueTime != null)
            throw InvalidTaskException("Task must have a due date")
        if(taskWithTagAndSubTaskToAdd.task.dueDate != null && taskWithTagAndSubTaskToAdd.task.dueTime != null && !taskWithTagAndSubTaskToAdd.task.isEnded ) {
            if(taskWithTagAndSubTaskToAdd.task.dueDate.isBefore(LocalDateTime.now().toLocalDate()))
                throw InvalidTaskException("Task due date must be in the future")
            if(taskWithTagAndSubTaskToAdd.task.dueDate.isEqual(LocalDateTime.now().toLocalDate()) && taskWithTagAndSubTaskToAdd.task.dueTime.isBefore(LocalDateTime.now().toLocalTime()))
                throw InvalidTaskException("Task due time must be in the future")
        }
        val id = repository.insertTask(taskWithTagAndSubTaskToAdd)
        Log.d("AddTaskUseCase", "id = $id")
        if(taskWithTagAndSubTaskToAdd.task.dueDate != null && taskWithTagAndSubTaskToAdd.task.dueTime != null && !taskWithTagAndSubTaskToAdd.task.isEnded){
            val dueDateTime = LocalDateTime.of(
                taskWithTagAndSubTaskToAdd.task.dueDate,
                taskWithTagAndSubTaskToAdd.task.dueTime
            ).atZone(
                ZoneId.systemDefault()
            ).toInstant().toEpochMilli()
            val inputData = workDataOf("task" to taskWithTagAndSubTaskToAdd.task.name)
            val notifierWorkerRequest = OneTimeWorkRequestBuilder<TaskNotifierWorker>()
                .setInitialDelay(dueDateTime - System.currentTimeMillis(), TimeUnit.MILLISECONDS)
                .setInputData(inputData)
                .build()

            val workManager = WorkManager.getInstance(context)
            workManager.beginUniqueWork(
                taskWithTagAndSubTaskToAdd.task.name + id,
                ExistingWorkPolicy.REPLACE,
                notifierWorkerRequest
            ).enqueue()
        }
        Log.d("AddTaskUseCase", "sortie de invoke")
        return id
    }
}