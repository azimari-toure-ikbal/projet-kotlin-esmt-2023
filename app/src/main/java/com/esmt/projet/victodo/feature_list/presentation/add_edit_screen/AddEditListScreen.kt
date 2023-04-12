package com.esmt.projet.victodo.feature_list.presentation.add_edit_screen

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.esmt.projet.victodo.R
import com.esmt.projet.victodo.feature_list.presentation.util.CircleColors

@Composable
fun AddEditListScreen() {
    Column(
        modifier = Modifier.fillMaxHeight()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
        ) {
            for (i in 6..11) {
                CircleCanvas(CircleColors.getArrayOfColors()[i])
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
        ) {
            for (i in 0..5) {
                CircleCanvas(CircleColors.getArrayOfColors()[i])
            }
        }
    }
}

@Composable
fun CircleCanvas(color: CircleColors) {
    Canvas(
        modifier = Modifier
            .padding(12.dp)
            .size(50.dp),
    ) {
        drawCircle(
            color = color.color,
            radius = 25f,
        )
    }
}

@Preview
@Composable
fun Preview() {
    AddEditListScreen()
}