package com.esmt.projet.victodo.feature_list.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.clipRect
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TaskItem() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(Color.Transparent)
            .border(1.5.dp, Color(0xFFedf4fe), RoundedCornerShape(10.dp))
            .padding(16.dp)
            .drawBehind {
                clipRect {
                    drawLine(
                        color = Color.Blue,
                        start = Offset(0f, 0f),
                        end = Offset(0f, size.height),
                        strokeWidth = 4.dp.toPx()
                    )
                }
            }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 8.dp, end = 8.dp, bottom = 8.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
            ) {
                Text(
                    text = "Task x",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color(0xFF006EE9)
                )
                Icon(
                    imageVector = Icons.Default.MoreVert,
                    contentDescription = null,
                    tint = Color(0xFFABCEF5),
                    modifier = Modifier.rotate(90f)
                )
            }
            Text(
                text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Donec auctor, nisl eget ultricies lacinia, nisl nisl aliquam nisl, eu aliquam nisl nisl sit amet nisl.",
                fontSize = 10.sp,
                fontWeight = FontWeight.Normal,
            )
            Text(
                text = "Feb, 21 - 21:00",
                fontSize = 10.sp,
                color = Color(0xFF0668E5),
                modifier = Modifier
                    .padding(top = 8.dp)
                    .align(Alignment.End)
            )
        }
    }
}
