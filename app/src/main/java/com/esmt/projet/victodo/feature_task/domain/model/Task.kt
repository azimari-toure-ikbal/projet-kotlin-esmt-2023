package com.esmt.projet.victodo.feature_task.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate
import java.time.LocalTime

@Entity(tableName = "task")
data class Task(
    @PrimaryKey(autoGenerate = true) val id: Long?=null,
    val name: String,
    val note: String="",
    val isEnded: Boolean=false,
    val priority: String=Priority.LOW.value,
    val dueDate: LocalDate?=null,
    val dueTime: LocalTime?=null,
    val redundancy: String=RepeatFrequency.NEVER.value,
    val listId: Long?=null,
    val timestamp: Long?=null
){
    companion object {
        sealed class RepeatFrequency(val value: String) {
            object NEVER : RepeatFrequency("Never")
            object DAILY : RepeatFrequency("Every day")
            object WEEKLY : RepeatFrequency("Every week")
            object MONTHLY : RepeatFrequency("Every month")
            object YEARLY : RepeatFrequency("Every year")
        }

        sealed class Priority(val value: String) {
            object HIGH : Priority("❗❗❗")
            object MEDIUM : Priority("❗❗")
            object LOW : Priority("❗")
        }
    }
}

