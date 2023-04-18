package com.esmt.projet.victodo.feature_task.domain.use_case

import com.esmt.projet.victodo.exception.task.InvalidTaskException
import com.esmt.projet.victodo.feature_task.domain.model.TaskWithTagAndSubTask
import com.esmt.projet.victodo.feature_task.domain.repository.TaskRepository

class DeleteTaskUseCase(
    val repository: TaskRepository
) {
    @Throws(InvalidTaskException::class)
    suspend operator fun invoke(taskWithTagAndSubTask: TaskWithTagAndSubTask) {
        if(taskWithTagAndSubTask.task.id ==null || taskWithTagAndSubTask.task.id <= 0)
            throw InvalidTaskException("Task to delete must have id")
        repository.deleteTask(taskWithTagAndSubTask)
    }
}