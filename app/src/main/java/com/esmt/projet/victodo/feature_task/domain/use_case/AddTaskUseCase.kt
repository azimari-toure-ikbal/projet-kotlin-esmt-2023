package com.esmt.projet.victodo.feature_task.domain.use_case

import com.esmt.projet.victodo.feature_task.domain.model.InvalidTaskException
import com.esmt.projet.victodo.feature_task.domain.model.TaskWithTagAndSubTask
import com.esmt.projet.victodo.feature_task.domain.repository.TaskRepository

class AddTaskUseCase (
    val repository: TaskRepository
) {
    @Throws(InvalidTaskException::class)
    suspend operator fun invoke(taskWithTagAndSubTask: TaskWithTagAndSubTask){
        if(taskWithTagAndSubTask.task.listId == null)
            throw InvalidTaskException("Task must be associated with a list")
        if(taskWithTagAndSubTask.task.name.isBlank())
            throw InvalidTaskException("Task must have a title")
        taskWithTagAndSubTask.tags?.let {tags->
            for(tag in tags) {
                if(tag.id == null)
                    throw InvalidTaskException("Tag must have an id")
            }
        }
        taskWithTagAndSubTask.subtasks?.let{subtasks->
            for(subTask in subtasks) {
                if(subTask.name.isBlank())
                    throw InvalidTaskException("SubTask must have a title")
            }
        }
        repository.insert(taskWithTagAndSubTask)
    }
}