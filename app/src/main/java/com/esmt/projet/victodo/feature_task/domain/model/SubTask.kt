package com.esmt.projet.victodo.feature_task.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlin.collections.List
@Entity
data class SubTask(
    @PrimaryKey val id: Int,
    val name: String,
    val note: String,
    val isEnded: Boolean,
    val taskId: Int,
    val listId: Int,
    private val tags: List<Tag>,
){
}