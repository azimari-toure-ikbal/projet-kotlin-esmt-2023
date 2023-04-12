package com.esmt.projet.victodo.feature_task.domain.model

import androidx.room.Entity
import androidx.room.Junction
import androidx.room.PrimaryKey
import androidx.room.Relation
import com.esmt.projet.victodo.feature_tag.domain.model.Tag
import java.time.LocalDate
import java.time.LocalTime
import kotlin.collections.List

@Entity(tableName = "task")
data class Task(
    @PrimaryKey val id: Long,
    val name: String,
    val note: String,
    val isEnded: Boolean,
    val priority: Int,
    val dueDate: LocalDate,
    val dueTime: LocalTime,
    val redundancy: Int,//By day
    val listId: Long,
)

class InvalidTaskException(message: String) : Exception(message)