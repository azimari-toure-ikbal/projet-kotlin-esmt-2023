package com.esmt.projet.victodo.feature_task.domain.model

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import com.esmt.projet.victodo.feature_tag.domain.model.Tag

data class TaskWithTagAndSubTask(
    @Embedded
    val task: Task,
    @Relation(
        parentColumn = "id",
        entityColumn = "id",
        entity = Tag::class,
        associateBy = Junction(value = TagTaskCrossRef::class, parentColumn = "tkId", entityColumn = "tgId")
    )
    val tags: List<Tag> = emptyList(),
    @Relation(
        parentColumn = "id",
        entity = SubTask::class,
        entityColumn = "taskId"
    )
    val subtasks: List<SubTask> = emptyList()
)