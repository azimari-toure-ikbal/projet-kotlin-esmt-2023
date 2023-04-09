package com.esmt.projet.victodo.feature_task.domain.model

import androidx.compose.ui.graphics.Color
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation
import kotlin.collections.List

@Entity(tableName = "lists")
data class List(
    @PrimaryKey val id: Int,
    val title: String,
    val color: Int,//to check
    @Relation(
        parentColumn = "id",
        entity = Task::class,
        entityColumn = "listId"
    )
    val tasks: List<Task>,
    val isPinned: Boolean,
    val icon: String,
    val isDefault: Boolean,
){
    companion object {
        val listColors = listOf(
            Color.Red,
            Color.LightGray,
            Color.Green,
            Color.Blue,
            Color.Magenta,
            Color.Yellow
        )
    }
}
