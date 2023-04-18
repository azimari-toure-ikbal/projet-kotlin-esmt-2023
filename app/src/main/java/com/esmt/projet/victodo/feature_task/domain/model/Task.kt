package com.esmt.projet.victodo.feature_task.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate
import java.time.LocalTime

@Entity(tableName = "task")
data class Task(
    @PrimaryKey val id: Long?=null,
    val name: String,
    val note: String?=null,
    val isEnded: Boolean=false,
    val priority: Int?=0,
    val dueDate: LocalDate?=null,
    val dueTime: LocalTime?=null,
    val redundancy: Int=0,//By day
    val listId: Long?=null,
)

