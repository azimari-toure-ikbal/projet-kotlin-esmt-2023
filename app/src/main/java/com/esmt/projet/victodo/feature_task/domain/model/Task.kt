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
    @PrimaryKey val id: Long?=0,
    val name: String,
    val note: String?=null,
    val isEnded: Boolean=false,
    val priority: Int?=0,
    val dueDate: LocalDate?=null,
    val dueTime: LocalTime?=null,
    val redundancy: Int=0,//By day
    val listId: Long?=null,
)

class InvalidTaskException(message: String) : Exception(message)