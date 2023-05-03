package com.esmt.projet.victodo.feature_task.presentation.add_edit_screen

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import android.widget.Toast
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat.shouldShowRequestPermissionRationale
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.esmt.projet.victodo.R
import com.esmt.projet.victodo.core.presentation.components.AddEditHeader
import com.esmt.projet.victodo.core.presentation.components.DropDownItem
import com.esmt.projet.victodo.feature_task.domain.model.Task
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.datetime.date.DatePickerDefaults
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import com.vanpra.composematerialdialogs.datetime.time.TimePickerDefaults
import com.vanpra.composematerialdialogs.datetime.time.timepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import kotlinx.coroutines.flow.collectLatest

import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AddEditTaskScreen(
    navController: NavController,
    viewModel: AddEditTaskViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val state = viewModel.state.value
    val pickedDate = state.dueDate
    val pickedTime = state.dueTime
    val repeatFrequency = state.repeatFrequency
    val priority = state.priority

    val taskNameState = viewModel.nameTextFieldState.value
    val taskNoteState = viewModel.noteTextFieldState.value
    val taskTagState = viewModel.tagTextField.value

    val showDeadlineOptions = state.showDeadlineOptions

    val tagList = viewModel.tagState.value.tagList

    val selectedTag = viewModel.tagState.value.selectedTags

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

    val taskId = navController.currentBackStackEntry?.arguments?.getLong("taskId")
    val listId = navController.previousBackStackEntry?.arguments?.getLong("listId")
    if(listId != null){
        viewModel.onEvent(AddEditTaskEvent.EnteredListId(listId))
    }

    LaunchedEffect(key1 = true){
        viewModel.eventFlow.collectLatest {
            when(it){
                AddEditTaskViewModel.UiEvent.SaveTask -> {
                    navController.navigateUp()
                }
                is AddEditTaskViewModel.UiEvent.ShowSnackBar -> {
                    Log.d("AddEditTaskScreen", "ShowSnackBar ${it.message}")
                    // TODO(
                    //  1. Add a snackbar to show the error message
                    //  2. Add an icon to the snackbar)
                    Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    AddEditHeader(
        title = if (taskId != null && taskId>0L) "Edit Task" else "New Task",
        navController = navController
    ) {
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
                maxLines = 1,
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
            Row {
                DropDownMenuCustom(
                    dropDownTitle = state.tasklists.find { it.id == state.listId }?.title
                        ?: "Select a list",
                    dropDownIcon = R.drawable.drop_down_menu_list_24px,
                    dropDownItems = state.tasklists.map {
                        DropDownItem(it.id!!, it.title)
                    },
                    onItemClick = {
                        Log.d("AddEditTaskScreen", "onItemClick: $it")
                        viewModel.onEvent(AddEditTaskEvent.EnteredListId(it.id))
                    }
                )
            }

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
                        if ( Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
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
                        positiveButton(
                            "OK",
                            textStyle = TextStyle(
                                color = Color(0xFF006EE9),
                            )
                        )
                        negativeButton(
                            "Cancel",
                            textStyle = TextStyle(
                                color = Color(0xFF006EE9),
                            )
                        )
                    },
                ) {
                    datepicker(
                        initialDate = pickedDate ?: LocalDate.now(),
                        title = "Pick a date",
                        colors = DatePickerDefaults.colors(
                            headerBackgroundColor = Color(0xFF006EE9),
                            headerTextColor = Color.White,
                            dateActiveBackgroundColor = Color(0xFF006EE9),
                        ),
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
                        positiveButton(
                            "OK",
                            textStyle = TextStyle(
                                color = Color(0xFF006EE9),
                            )
                        )
                        negativeButton(
                            "Cancel",
                            textStyle = TextStyle(
                                color = Color(0xFF006EE9),
                            )
                        )
                    },
                ) {
                    timepicker(
                        initialTime = pickedTime ?: LocalTime.now(),
                        title = "Pick a time",
                        colors = TimePickerDefaults.colors(
                            headerTextColor = Color.White,
                            activeBackgroundColor = Color(0xFF006EE9),
                            selectorColor = Color(0xFF006EE9),
                        ),
                        is24HourClock = true
                    ) {
                        viewModel.onEvent(AddEditTaskEvent.EnteredDueTime(it))
                        Log.d("AddEditTaskScreen", "Time: $it")
                    }
                }
                Spacer(modifier = Modifier.height(24.dp))

                DropDownMenuCustom(
                    dropDownTitle = repeatFrequency,
                    dropDownIcon = R.drawable.drop_down_menu_repeat_24px,
                    dropDownItems = listOf(
                        DropDownItem(4, Task.Companion.RepeatFrequency.NEVER.value),
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
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color(0xFF006EE9),
                    contentColor = Color(0xFFedf4fe)
                ),
                onClick = {
                    viewModel.onEvent(AddEditTaskEvent.CreateTag)
                }
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "add task",
                    modifier = Modifier
                        .size(16.dp)
                )
            }

            LazyRow(
                userScrollEnabled = false,
            ) {
                items(tagList) { tag ->
                    Box(
                        modifier = Modifier
                            .padding(8.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .background(
                                if (selectedTag.contains(tag)) {
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
                                if (selectedTag.contains(tag)) {
                                    viewModel.onEvent(AddEditTaskEvent.RemovedTag(tag))
                                } else {
                                    viewModel.onEvent(AddEditTaskEvent.EnteredTag(tag))
                                }
                            }
                    ) {
                        Text(
                            text = tag.title,
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
            Row {
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
            }

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
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color(0xFF006EE9),
                    contentColor = Color(0xFFedf4fe)
                ),
                onClick = {
                    Log.d("AddEditTaskScreen", "Save button clicked")
                    viewModel.onEvent(AddEditTaskEvent.SaveTask)
                }
            ) {
                Text(text = if(taskId!= null && taskId>0L) "Update" else "Save")
            }
        }
    }
}

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

    var itemWidth by remember {
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
                itemWidth = with(density) { it.width.toDp() }
            }
            .pointerInput(key1 = true) {
                detectTapGestures {
                    isContextMenuVisible = true
                    pressOffset = DpOffset(it.x.toDp(), it.y.toDp())
                    Log.d("DropDownMenuCustom", "pressOffset: $pressOffset")
                    Log.d("DropDownMenuCustom", "it : $it")
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
        offset = pressOffset.copy(y = pressOffset.y - itemHeight, x=itemWidth*0.65f)
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
//    AddEditTaskScreen()
}
