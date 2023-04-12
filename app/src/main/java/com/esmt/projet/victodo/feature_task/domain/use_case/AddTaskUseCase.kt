package com.esmt.projet.victodo.feature_task.domain.use_case

import com.esmt.projet.victodo.feature_task.domain.model.InvalidTaskException
import com.esmt.projet.victodo.feature_task.domain.model.TaskWithTagsAndSubTasks
import com.esmt.projet.victodo.feature_task.domain.repository.TaskRepository

class AddTaskUseCase (
    val repository: TaskRepository
) {
    @Throws(InvalidTaskException::class)
    suspend operator fun invoke(taskWithTagsAndSubTasks: TaskWithTagsAndSubTasks) {
        if(taskWithTagsAndSubTasks.task.listId == 0L)
            throw InvalidTaskException("Task must be associated with a list")
        if(taskWithTagsAndSubTasks.task.name.isBlank())
            throw InvalidTaskException("Task must have a title")
        for(tag in taskWithTagsAndSubTasks.tags) {
            if(tag.id!! < 1L)
                throw InvalidTaskException("Tag must have an id")
        }
        for(subTask in taskWithTagsAndSubTasks.subtasks) {
            if(subTask.name.isBlank())
                throw InvalidTaskException("SubTask must have a title")
        }
        repository.insert(taskWithTagsAndSubTasks)
    }
}