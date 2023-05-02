package com.esmt.projet.victodo.core.presentation.components

import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.esmt.projet.victodo.feature_list.domain.model.TaskListWithTasksAndTagsSubTasks


@Composable
fun TaskListItem(
    taskList: TaskListWithTasksAndTagsSubTasks,
    dropDownItems: List<DropDownItem>,
    onItemClick: (DropDownItem) -> Unit,
    onListClick: () -> Unit,
) {

    var isContextMenuVisible by rememberSaveable {
        mutableStateOf(false)
    }

    var pressOffset by remember {
        mutableStateOf(DpOffset.Zero)
    }

    var itemHeight by remember {
        mutableStateOf(0.dp)
    }

    val density = LocalDensity.current

    val interactionSource = remember {
        MutableInteractionSource()
    }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .indication(
                    interactionSource,
                    LocalIndication.current
                )
                .onSizeChanged {
                    itemHeight = with(density) { it.height.toDp() }
                }
                .pointerInput(key1 = true) {
                    detectTapGestures(
                        onLongPress = {
                            isContextMenuVisible = true
                            pressOffset = DpOffset(it.x.toDp(), it.y.toDp())
                        },
                        onPress = {
                            val press = PressInteraction.Press(it)
                            interactionSource.emit(press)
                            tryAwaitRelease()
                            interactionSource.emit(PressInteraction.Release(press))
                        },
                        onTap = {
                            onListClick()
                        }
                    )
                }
                .height(55.dp)
                .padding(bottom = 10.dp, end = 18.dp)
                .border(
                    1.dp,
                    color = Color(0xFFedf4fe),
                    shape = RoundedCornerShape(10.dp)
                )
//                .clickable {
//                    onListClick()
//                }
        ) {
            Text(
                text = taskList.taskList.title,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                modifier = Modifier
                    .padding(start = 15.dp)
            )
            Text(
                text = taskList.tasks.size.toString(),
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                modifier = Modifier
                    .padding(end = 15.dp)
            )

        }

    DropdownMenu(
        expanded = isContextMenuVisible,
        onDismissRequest = { isContextMenuVisible = false },
        offset = pressOffset.copy(
            y = pressOffset.y - itemHeight
        )
    ) {
        dropDownItems.forEach { item ->
            DropdownMenuItem(
                onClick = {
                    onItemClick(item)
                    isContextMenuVisible = false
                }
            ) {
                Text(
                    text = item.text
                )
            }
        }
    }

//        Box(
//            modifier = Modifier
//                .fillMaxWidth()
//                .pointerInput(key1 = true) {
//                    detectTapGestures(
//                        onLongPress = {
//                            isContextMenuVisible = true
//                            pressOffset = DpOffset(it.x.toDp(), it.y.toDp())
//                        },
//                    )
//                }
//                .padding(16.dp)
//        ) {
//            Text(
//                text = taskList.title,
//            )
//            DropdownMenu(
//                expanded = isContextMenuVisible,
//                onDismissRequest = { isContextMenuVisible = false },
//            ) {
//                dropDownItems.forEach { item ->
//                    DropdownMenuItem(
//                        onClick = {
//                            onItemClick(item)
//                            isContextMenuVisible = false
//                        }
//                    ) {
//                        Text(
//                            text = item.text
//                        )
//                    }
//                }
//            }
//        }

}