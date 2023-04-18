package com.esmt.projet.victodo.feature_list.presentation.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.esmt.projet.victodo.feature_list.presentation.util.CircleColors

@Composable
fun CircleColorPicker() {
    for (i in 0 until CircleColors.size()) {
        Box(
            modifier = Modifier
                .size(42.dp)
                .clip(CircleShape)
                .clickable {

                }
        ) {
            CircleCanvas(color = CircleColors.getArrayOfColors()[i])
        }
    }
}

@Composable
fun CircleCanvas(color: CircleColors) {
    Canvas(
        modifier = Modifier
            .padding(4.dp)
            .size(50.dp),
    ) {
        drawCircle(
            color = color.color,
            radius = 62f,
        )
    }
}