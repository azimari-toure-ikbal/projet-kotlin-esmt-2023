package com.esmt.projet.victodo.feature_list.domain.model

import androidx.compose.ui.graphics.Color
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.esmt.projet.victodo.R

@Entity(tableName = "lists")
data class TaskList(
    @PrimaryKey(autoGenerate = true) val id: Long?=null,
    val title: String,
    val color: Int = 1,//to check
    val isPinned: Boolean = false,
    val icon: Int = listIcons[0],
    val isDefault: Boolean = false,
){
    companion object {

        val listIcons = listOf(
            R.drawable.list_icon1_24px,
            R.drawable.list_icon2_24px,
            R.drawable.list_icon3_24px,
            R.drawable.list_icon4_24px,
            R.drawable.list_icon5_24px,
            R.drawable.list_icon6_24px,
            R.drawable.list_icon7_24px,
            R.drawable.list_icon8_24px,
            R.drawable.list_icon9_24px,
            R.drawable.list_icon10_24px,
            R.drawable.list_icon11_24px,
            R.drawable.list_icon12_24px,
            R.drawable.list_icon13_24px,
            R.drawable.list_icon14_24px,
            R.drawable.list_icon15_24px,
            R.drawable.list_icon16_24px,
            R.drawable.list_icon17_24px,
            R.drawable.list_icon18_24px,
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
