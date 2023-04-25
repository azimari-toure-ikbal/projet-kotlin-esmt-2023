package com.esmt.projet.victodo.feature_list.presentation.list_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Build
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.esmt.projet.victodo.core.presentation.components.DropDownItem
import com.esmt.projet.victodo.feature_list.presentation.components.TaskItem
import com.esmt.projet.victodo.feature_tag.domain.model.Tag
import com.esmt.projet.victodo.feature_task.domain.model.Task
import com.esmt.projet.victodo.feature_task.domain.model.TaskWithTagAndSubTask

@Composable
fun ListWithTasksScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 32.dp, start = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                imageVector = Icons.Default.Build,
                contentDescription = null,
                tint = Color.Blue
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp)
            ) {
                Text(
                    text = "Workout",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .padding(end = 16.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(Color.Blue)
                        .padding(8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = null,
                        tint = Color.White,
                    )
                    Text(
                        text = "Add Task",
                        color = Color.White,
                    )
                }
            }
        }
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 32.dp)
        ) {
            items(10) {
                TaskItem(
                    dropDownItems = listOf(
                        DropDownItem(1, "Edit"),
                        DropDownItem(2, "Delete")
                    ),
                    onItemClick = {},
                    task = TaskWithTagAndSubTask(
                        task = Task(
                            id = 1,
                            name = "Task 1",
                            note = "asldhjlakdalsjdklajldjaljdlakjsd;kas;djk;asda;on jjf djf;ajf;jf sd;fj sdjf ;ajf;ldj a"
                        ),
                        tags = listOf(
                            Tag(
                                id = 1,
                                title = "Workout",
                            ),
                            Tag(
                                id = 2,
                                title = "Programming",
                            ),
                            Tag(
                                id = 3,
                                title = "Chess",
                            ),
                        ),
                    )
                )
            }
        }
    }
}

@Preview
@Composable
fun Preview() {
    ListWithTasksScreen()
}