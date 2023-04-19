package com.esmt.projet.victodo.feature_list.domain.model

import androidx.compose.ui.graphics.Color
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.esmt.projet.victodo.R

@Entity(tableName = "lists")
data class TaskList(
    @PrimaryKey val id: Long? =null,
    val title: String,
    val color: Int = 1,//to check
    val isPinned: Boolean = false,
    val icon: Int = 0,
    val isDefault: Boolean = false,
){
    companion object {

        val listIcons = listOf(
            R.drawable.ic_launcher_foreground,
            R.drawable.ic_launcher_foreground,
        )

        val listColors = listOf(
            Color(0xFFCB2F2F),
            Color(0xFF006EE9),
            Color(0xFF35AB4F),
            Color(0xFF7B61FF),
            Color(0xFFF24E1E),
            Color(0xFFFF7262),
            Color(0xFF362075),
            Color(0xFF434343),
            Color(0xFF97C8FF),
            Color(0xFFEBDBCF),
            Color(0xFFFFE7AA),
            Color(0xFFB66200)
        )
    }
}
