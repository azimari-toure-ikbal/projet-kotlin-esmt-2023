package com.esmt.projet.victodo.feature_list.domain.model

import androidx.compose.ui.graphics.Color
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.esmt.projet.victodo.feature_task.domain.model.Tag
import com.esmt.projet.victodo.feature_task.domain.model.Task
import kotlin.collections.List

@Entity
data class List(
    @PrimaryKey val id: Int,
    val title: String,
    val color: Int,//to check
    val tasks: List<Task>,
    val tags: List<Tag>,
    val isPinned: Boolean,
    val icon: String,
    val isDefault: Boolean,
){
    companion object {
        val listColors = listOf(
            Color.Red,
            Color.LightGray,
            Color.Green,
            Color.Blue, Color.Magenta,
            Color.Yellow
        )
    }
}
