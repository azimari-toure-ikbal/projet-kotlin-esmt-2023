package com.esmt.projet.victodo.feature_task.presentation.add_edit_screen

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat.shouldShowRequestPermissionRationale
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import com.esmt.projet.victodo.R
import com.esmt.projet.victodo.core.presentation.components.AddEditHeader
import com.esmt.projet.victodo.core.presentation.components.DropDownItem
import com.esmt.projet.victodo.feature_tag.domain.model.Tag
import com.esmt.projet.victodo.feature_task.domain.model.Task
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import com.vanpra.composematerialdialogs.datetime.time.timepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState

import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AddEditTaskScreen(
    viewModel: AddEditTaskViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val state = viewModel.state.value
    val pickedDate = state.dueDate
    val pickedTime = state.dueTime
    val repeatFrequency = state.repeatFrequency
    val priority = state.priority
    val tags = state.tags

    val taskNameState = viewModel.nameTextFieldState.value
    val taskNoteState = viewModel.noteTextFieldState.value
    val taskTagState = viewModel.tagTextField.value

    val showDeadlineOptions = state.showDeadlineOptions

    var tagList = tags.map {
        TagItem(tagColor = viewModel.tagColor.value, tag = it)
    }

    val selectedTag = state.selectedTags.map {
        TagItem(tagColor = viewModel.tagColor.value, tag = it)
    }

    val permissionLauncher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            if (!isGranted) {
                AlertDialog.Builder(context)
                    .setTitle("Permission needed")
                    .setMessage("You have denied this permission. Please allow it in the settings")
                    .setNeutralButton("ok") { dialog, _ ->
                        dialog.dismiss()
                    }
                    .create().show()
            }
        }


    AddEditHeader(title = "New Task") {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Title(title = "Title")
            OutlinedTextField(
                value = taskNameState.text,
                onValueChange = {
                    viewModel.onEvent(AddEditTaskEvent.EnteredName(it))
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                singleLine = true,
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color(0xFFedf4fe),
                    unfocusedBorderColor = Color(0xFFedf4fe),
                    cursorColor = Color(0xFFedf4fe),
                    textColor = Color.DarkGray,
                ),
                shape = RoundedCornerShape(10.dp),
            )
            Spacer(modifier = Modifier.height(24.dp))
            Title(title = "List")
            DropDownMenuCustom(
                dropDownTitle = "Select a list",
                dropDownIcon = R.drawable.drop_down_menu_list_24px,
                dropDownItems = state.tasklists.map {
                    DropDownItem(it.id!!, it.title)
                },
                onItemClick = {
                    viewModel.onEvent(AddEditTaskEvent.EnteredListId(it.id))
                }
            )

            Spacer(modifier = Modifier.height(24.dp))
            Title(title = "Description")
            OutlinedTextField(
                value = taskNoteState.text,
                onValueChange = {
                    viewModel.onEvent(AddEditTaskEvent.EnteredNote(it))
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp)
                    .height(100.dp),
                maxLines = 10,
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color(0xFFedf4fe),
                    unfocusedBorderColor = Color(0xFFedf4fe),
                    cursorColor = Color(0xFFedf4fe),
                    textColor = Color.DarkGray,
                ),
                shape = RoundedCornerShape(10.dp),
            )
            Spacer(modifier = Modifier.height(24.dp))
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Title(title = "Deadline")
                Switch(
                    checked = showDeadlineOptions,
                    onCheckedChange = {
                        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.TIRAMISU) {
                            if (ContextCompat.checkSelfPermission(
                                    context,
                                    Manifest.permission.POST_NOTIFICATIONS
                                ) != PackageManager.PERMISSION_GRANTED
                            ) {
                                if (shouldShowRequestPermissionRationale(
                                        context as Activity,
                                        Manifest.permission.POST_NOTIFICATIONS
                                    )
                                ) {
                                    AlertDialog.Builder(context)
                                        .setTitle("Notification Permission needed")
                                        .setMessage("This permission is needed to set a reminder")
                                        .setPositiveButton("ok") { dialog, _ ->
                                            dialog.dismiss()
                                            permissionLauncher.launch(
                                                Manifest.permission.POST_NOTIFICATIONS
                                            )
                                        }
                                        .setNegativeButton("cancel") { dialog, _ ->
                                            dialog.dismiss()
                                            viewModel.onEvent(AddEditTaskEvent.ToggleDeadlineOptions)
                                        }
                                        .create().show()
                                } else {
                                    permissionLauncher.launch(
                                        Manifest.permission.POST_NOTIFICATIONS
                                    )
                                }
                            }
                        }
                        viewModel.onEvent(AddEditTaskEvent.ToggleDeadlineOptions)

                    },
                    colors = SwitchDefaults.colors(
                        checkedThumbColor = Color.White,
                        checkedTrackColor = Color(0xFF006EE9),
                    ),
                )
            }

            if (showDeadlineOptions) {
                Spacer(modifier = Modifier.height(24.dp))

                val dateDialogState = rememberMaterialDialogState()
                val timeDialogState = rememberMaterialDialogState()
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(55.dp)
                        .border(1.5.dp, Color(0xFFedf4fe), RoundedCornerShape(10.dp))
                        .clickable {
                            dateDialogState.show()
                        }
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.add_task_screen_calendar_24px),
                        contentDescription = null,
                        modifier = Modifier
                            .padding(8.dp)
                            .align(Alignment.CenterVertically)
                    )
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .padding(8.dp)
                            .align(Alignment.CenterVertically)
                    ) {
                        Text(
                            text = "Date",
                            color = Color.DarkGray,
                            fontSize = 12.sp,
                        )
                        Text(
                            text = (pickedDate ?: LocalDate.now()).format(DateTimeFormatter.ofPattern("MM-dd-yyyy"))
                                .toString(),
                            color = Color(0xFF006EE9),
                            fontSize = 10.sp,
                        )
                    }
                }
                Spacer(modifier = Modifier.height(24.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(55.dp)
                        .border(1.5.dp, Color(0xFFedf4fe), RoundedCornerShape(10.dp))
                        .clickable {
                            timeDialogState.show()
                        }
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.add_task_screen_time_24px),
                        contentDescription = null,
                        modifier = Modifier
                            .padding(8.dp)
                            .align(Alignment.CenterVertically)
                    )
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .padding(8.dp)
                            .align(Alignment.CenterVertically)
                    ) {
                        Text(
                            text = "Time",
                            color = Color.DarkGray,
                            fontSize = 12.sp,
                        )
                        Text(
                            text = (pickedTime ?: LocalTime.now()).format(DateTimeFormatter.ofPattern("hh:mm"))
                                .toString(),
                            color = Color(0xFF006EE9),
                            fontSize = 10.sp,
                        )
                    }
                }

                MaterialDialog(
                    dialogState = dateDialogState,
                    buttons = {
                        positiveButton("OK")
                        negativeButton("Cancel")
                    },
                ) {
                    datepicker(
                        initialDate = LocalDate.now(),
                        title = "Pick a date",
                        allowedDateValidator = {
                            it.isAfter(LocalDate.now().minusDays(1))
                        }
                    ) {
                        viewModel.onEvent(AddEditTaskEvent.EnteredDueDate(it))
                        Log.d("AddEditTaskScreen", "Date: $it")
                    }
                }

                MaterialDialog(
                    dialogState = timeDialogState,
                    buttons = {
                        positiveButton("OK")
                        negativeButton("Cancel")
                    },
                ) {
                    timepicker(
                        initialTime = LocalTime.now(),
                        title = "Pick a time",
                    ) {
                        viewModel.onEvent(AddEditTaskEvent.EnteredDueTime(it))
                        Log.d("AddEditTaskScreen", "Time: $it")
                    }
                }
                Spacer(modifier = Modifier.height(24.dp))

                DropDownMenuCustom(
                    dropDownTitle = if(repeatFrequency.equals(Task.Companion.RepeatFrequency.NEVER.value)) "Repeat" else repeatFrequency,
                    dropDownIcon = R.drawable.drop_down_menu_repeat_24px,
                    dropDownItems = listOf(
                        DropDownItem(0, Task.Companion.RepeatFrequency.DAILY.value),
                        DropDownItem(1, Task.Companion.RepeatFrequency.WEEKLY.value),
                        DropDownItem(2, Task.Companion.RepeatFrequency.MONTHLY.value),
                        DropDownItem(3, Task.Companion.RepeatFrequency.YEARLY.value),
                    ),
                    onItemClick = {
                        viewModel.onEvent(AddEditTaskEvent.EnteredRedundancy(it.text))
                    }
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Spacer(modifier = Modifier.height(24.dp))
            Title(title = "Tags")
            Spacer(modifier = Modifier.height(8.dp))
            OutlinedTextField(
                value = if(taskTagState.isHintVisible) taskTagState.hint else taskTagState.text ,
                onValueChange = {
                    viewModel.onEvent(AddEditTaskEvent.EnteredTagText(it))
                    tagList = tags.filter { tag -> tag.title.lowercase().contains(it.lowercase()) }.map { tagFinal->
                        TagItem(
                            tagColor = viewModel.tagColor.value,
                            tag = tagFinal
                        )
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp)
                    .onFocusChanged {
                        viewModel.onEvent(AddEditTaskEvent.TagTextFieldFocused(it))
                    },
                singleLine = true,
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = Color(0xFFedf4fe),
                    unfocusedBorderColor = Color(0xFFedf4fe),
                    cursorColor = Color(0xFFedf4fe),
                    textColor = Color.DarkGray,
                ),
                shape = RoundedCornerShape(10.dp),
            )
            Button(
                onClick = {
                    if(tags.filter { tag -> tag.title.lowercase().contains(taskTagState.text.lowercase()) }.isEmpty()) {
                        viewModel.onEvent(AddEditTaskEvent.CreateTag)
                    }
                }
            ) {
                Text(text = "+")
                // TODO(handle button )
            }

            LazyRow(
                userScrollEnabled = false,
            ) {
                items(tagList) { tagItem ->
                    Box(
                        modifier = Modifier
                            .padding(8.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .background(
                                if (selectedTag.contains(tagItem)) {
                                    Color(0xFF006EE9)
                                } else {
                                    Color(0xFFedf4fe)
                                }
                            )
                            .border(
                                1.dp,
                                color = Color(0xFFedf4fe),
                                shape = RoundedCornerShape(10.dp)
                            )
                            .clickable {
                                if (selectedTag.contains(tagItem)) {
                                    viewModel.onEvent(AddEditTaskEvent.RemovedTag(tagItem.tag))
                                } else {
                                    viewModel.onEvent(AddEditTaskEvent.EnteredTag(tagItem.tag))
                                }
                            }
                    ) {
                        Text(
                            text = tagItem.tag.title,
                            fontSize = 14.sp,
                            modifier = Modifier
                                .padding(4.dp)
                        )
                    }

                }
            }

//            FlowRow(
//                maxItemsInEachRow = 4,
//                modifier = Modifier
//                    .fillMaxWidth()
//            ) {
//                for (i in 0..6) {
//                    TagItem(
//                        tagColor = viewModel.tagColor.value,
//                        tagTitle = "Tag $i c"
//                    )
//                }
//            }

            Spacer(modifier = Modifier.height(24.dp))
            Title(title = "Priority")
            Spacer(modifier = Modifier.height(8.dp))
            DropDownMenuCustom(
                dropDownTitle = priority,
                dropDownIcon = R.drawable.drop_down_menu_priority_24px,
                dropDownItems = listOf(
                    DropDownItem(0, Task.Companion.Priority.LOW.value),
                    DropDownItem(1, Task.Companion.Priority.MEDIUM.value),
                    DropDownItem(2, Task.Companion.Priority.HIGH.value),
                ),
                onItemClick = {
                    viewModel.onEvent(AddEditTaskEvent.EnteredPriority(it.text))
                }
            )

            Spacer(modifier = Modifier.height(24.dp))
//            Title(title = "Subtasks")
//            OutlinedTextField(
//                value = "0",
//                onValueChange = {},
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(top = 8.dp),
//                singleLine = true,
//                colors = TextFieldDefaults.outlinedTextFieldColors(
//                    focusedBorderColor = Color(0xFFedf4fe),
//                    unfocusedBorderColor = Color(0xFFedf4fe),
//                    cursorColor = Color(0xFFedf4fe),
//                    textColor = Color.DarkGray,
//                ),
//                shape = RoundedCornerShape(10.dp),
//            )

            Button(
                onClick = {
                    viewModel.onEvent(AddEditTaskEvent.SaveTask)
                }
            ) {
                Text(text = "Save")
                // TODO(handle button )
            }
        }
    }
}

data class TagItem(
    val tagColor: Color,
    val tag: Tag,
)

@Composable
fun DropDownMenuCustom(
    dropDownTitle: String,
    dropDownIcon: Int,
    dropDownItems: List<DropDownItem>,
    onItemClick: (DropDownItem) -> Unit
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

    val triangleIcon: ImageVector = if (isContextMenuVisible) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(55.dp)
            .clip(MaterialTheme.shapes.medium)
            .background(Color.Transparent)
            .border(1.5.dp, Color(0xFFedf4fe), RoundedCornerShape(10.dp))
            .padding(8.dp)
            .onSizeChanged {
                itemHeight = with(density) { it.height.toDp() }
            }
            .pointerInput(key1 = true) {
                detectTapGestures {
                    isContextMenuVisible = true
                    pressOffset = DpOffset(it.x.toDp(), it.y.toDp())
                }
            },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = dropDownIcon),
            contentDescription = null
        )
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(8.dp)
                .align(Alignment.CenterVertically)
        ) {
            Text(
                text = dropDownTitle,
                color = Color.DarkGray,
                fontSize = 12.sp,
            )
        }
        Icon(
            imageVector = triangleIcon,
            contentDescription = "Dropdown icon",
            tint = Color.Blue
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
}

@Composable
fun Title(
    title: String
) {
    Text(
        text = title,
        fontSize = 14.sp,
        fontWeight = FontWeight.SemiBold,
        color = Color.Blue
    )
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun Preview() {
    AddEditTaskScreen()
}
