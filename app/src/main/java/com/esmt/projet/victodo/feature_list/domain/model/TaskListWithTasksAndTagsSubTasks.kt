package com.esmt.projet.victodo.feature_list.domain.model

import androidx.room.Embedded
import androidx.room.Relation
import com.esmt.projet.victodo.feature_task.domain.model.Task
import com.esmt.projet.victodo.feature_task.domain.model.TaskWithTagAndSubTask

data class TaskListWithTasksAndTagsSubTasks (
    @Embedded
    val taskList: TaskList,
    @Relation(
        parentColumn = "id",
        entity = Task::class,
        entityColumn = "listId"
    )
    val tasks: List<TaskWithTagAndSubTask>
)