package com.esmt.projet.victodo.feature_task.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate
import java.time.LocalTime
import kotlin.collections.List

@Entity
data class Task(
    @PrimaryKey val id: Int,
    val name: String,
    val note: String,
    val isEnded: Boolean,
    val priority: Int,
    val dueDate: LocalDate,
    val dueTime: LocalTime,
    val redundancy: Int,//to check
    val tags: List<Tag>,
    private val subtasks: List<SubTask>,
    private val listId: Int,
    ){
}
