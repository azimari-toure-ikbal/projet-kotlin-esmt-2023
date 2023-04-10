package com.esmt.projet.victodo.feature_list.domain.model

import androidx.compose.ui.graphics.Color
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "lists")
data class TaskList(
    @PrimaryKey val id: Long,
    val title: String,
    val color: Int,//to check
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
