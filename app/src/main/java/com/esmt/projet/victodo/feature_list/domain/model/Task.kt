package com.esmt.projet.victodo.feature_x.domain.model

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Blue
import androidx.compose.ui.graphics.Color.Companion.Green
import androidx.compose.ui.graphics.Color.Companion.LightGray
import androidx.compose.ui.graphics.Color.Companion.Red
import androidx.compose.ui.graphics.Color.Companion.Yellow
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
