package com.esmt.projet.victodo.feature_list.presentation.components

import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.clipRect
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.esmt.projet.victodo.core.presentation.components.DropDownItem
import com.esmt.projet.victodo.feature_task.domain.model.TaskWithTagAndSubTask
import java.time.LocalDate
import java.time.LocalTime

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun TaskItem(
    dropDownItems: List<DropDownItem>,
    onItemClick: (DropDownItem) -> Unit,
    task: TaskWithTagAndSubTask
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

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
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
                    }
                )
            }
            .clip(RoundedCornerShape(10.dp))
            .background(Color.Transparent)
            .border(
                1.5.dp,
                if (task.task.dueTime != null &&
                    task.task.dueDate != null &&
                    ((LocalTime.now().isAfter(task.task.dueTime)
                            && LocalDate.now().isEqual(task.task.dueDate)) ||
                            LocalDate.now().isAfter(task.task.dueDate)) &&
                    !task.task.isEnded
                ) {
                    Color.Red
                } else {
                    Color(0xFFedf4fe)
                },
                RoundedCornerShape(10.dp)
            )
            .padding(16.dp)
            .drawBehind {
                clipRect {
                    drawLine(
                        color =
                        if (task.task.isEnded) {
                            Color(0XFF4A934A)
                        } else {
                            Color(0xFF006EE9)
                        },
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
                    text = "${task.task.name} - ${task.task.priority}",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold,
                    color =
                    if (task.task.isEnded) {
                        Color(0XFF4A934A)
                    } else {
                        Color(0xFF006EE9)
                    }
                )

                if (task.task.isEnded) {
                    Icon(
                        imageVector = Icons.Default.CheckCircle,
                        contentDescription = null,
                        tint = Color(0XFF4A934A),
                        modifier = Modifier
                            .size(28.dp)
                            .clickable {
                                isContextMenuVisible = true
                                pressOffset = DpOffset(120.dp, (-60).dp)
                            }
                    )
                } else {
                    Icon(
                        imageVector = Icons.Default.MoreVert,
                        contentDescription = null,
                        tint = Color(0xFFABCEF5),
                        modifier = Modifier
                            .rotate(90f)
                    )
                }
            }
            Text(
                text = task.task.note,
                fontSize = 10.sp,
                fontWeight = FontWeight.Normal,
            )

            if (task.task.dueDate != null && task.task.dueTime != null) {
                Text(
                    text = "${task.task.dueDate} - ${task.task.dueTime}",
                    fontSize = 10.sp,
                    color =
                    if (LocalTime.now().isAfter(task.task.dueTime) && LocalDate.now().isAfter(task.task.dueDate)) {
                        Color.Red
                    } else {
                        Color(0xFF0668E5)
                    },
                    modifier = Modifier
                        .padding(top = 8.dp)
                        .align(Alignment.End)
                )

                if (task.task.redundancy != "" ) {
                    Text(
                        text = task.task.redundancy,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Normal,
                        color = Color(0xFF0668E5)
                    )
                }
            }

            FlowRow(
                maxItemsInEachRow = 3,
                horizontalArrangement = Arrangement.Start,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                task.tags.forEach { tag ->
                    Text(
                        text = "# ${tag.title}",
                        fontSize = 10.sp,
                        color = Color(0xFF0668E5),
                        modifier = Modifier
                            .padding(top = 8.dp)
                    )
                }
            }

        }
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

}
