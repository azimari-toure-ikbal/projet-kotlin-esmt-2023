package com.esmt.projet.victodo.feature_list.presentation.list_screen

import android.app.AlertDialog
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.esmt.projet.victodo.core.presentation.components.DropDownItem
import com.esmt.projet.victodo.feature_list.domain.model.TaskList
import com.esmt.projet.victodo.feature_list.presentation.components.TaskItem
import com.esmt.projet.victodo.feature_task.domain.model.TaskWithTagAndSubTask
import com.esmt.projet.victodo.R
import com.esmt.projet.victodo.core.presentation.util.Screen

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ListWithTasksScreen(
    navController: NavController,
    taskList: TaskList,
    viewModel: ListWithTasksViewModel = hiltViewModel()
) {
    val isTaskLoading = viewModel.state.value.isTaskLoading
//    val taskList = viewModel.state.value.taskList
    val tasksWithTagAndSubTaskList = viewModel.state.value.listOfTasks
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 32.dp, start = 16.dp)
                .clickable {
                    navController.popBackStack()
                },
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Back",
                tint = Color(0xFF006EE9),
                modifier = Modifier
                    .size(22.dp)
                    .padding(end = 4.dp)
            )
            Text(
                text = "Back",
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF006EE9),
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 32.dp, start = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(id = taskList.icon),
                contentDescription = null,
                tint = Color(0xFF006EE9),
                modifier = Modifier
                    .size(26.dp)
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp),
                ) {
                Text(
                    text = taskList.title,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                )
                if(taskList.id!! > 0) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .padding(end = 16.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .background(Color(0xFF006EE9))
                            .padding(8.dp)
                            .clickable {
                                navController.navigate(Screen.AddEditTaskScreen.route)
                            }
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
        }
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 32.dp)
        ) {
            if(isTaskLoading){
                item {
                    CircularProgressIndicator()
                }
            } else{
                if(tasksWithTagAndSubTaskList.isNotEmpty()){
                    items(tasksWithTagAndSubTaskList) { taskWithTagAndSubTask ->
                        TaskItem(
                            dropDownItems = if(!taskWithTagAndSubTask.task.isEnded) {
                                listOf(
                                    DropDownItem(1, "Edit"),
                                    DropDownItem(2, "Delete"),
//                                if (!taskWithTagAndSubTask.task.isEnded) {
//                                    DropDownItem(3, "Mark as Completed")
//                                } else {
//                                    DropDownItem(4, "Mark as Uncompleted")
//                                }
                                    DropDownItem(3, "Mark as Completed")
                                )
                            } else {
                                listOf(
                                    DropDownItem(2, "Delete")
                                )
                            },
                            onItemClick = {
                                when (it.id) {
                                    1L -> {
                                        navController.navigate(
                                            Screen.AddEditTaskScreen.route +
                                                    "?taskId=${taskWithTagAndSubTask.task.id}"
                                        )
                                    }
                                    2L -> {
                                        confirmDeleteTask(context, taskWithTagAndSubTask, viewModel)
                                    }
                                    3L -> {
                                        viewModel.onEvent(ListWithTasksEvent.OnCompletedClick(taskWithTagAndSubTask))
                                    }
                                    4L -> {
                                        // TODO()
                                        // viewModel.onEvent(ListWithTasksEvent.OnUncompletedClick(taskWithTagAndSubTask))
                                    }
                                }
                            },
                            task = taskWithTagAndSubTask
                        )
                    }
                } else {
                    item {
                        Image(
                            painter = painterResource(id = R.drawable.no_data),
                            contentDescription = "No Data",
                            alignment = Alignment.Center,
                            modifier = Modifier
                                .padding(top = 48.dp)
                        )
                    }
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
fun confirmDeleteTask(context: Context, taskWithTagAndSubTask: TaskWithTagAndSubTask, viewModel: ListWithTasksViewModel) {
    val builder = AlertDialog.Builder(context)
    builder.apply {
        setTitle("Delete Task")
        setMessage("Are you sure you want to delete this task?")
        setPositiveButton("Yes") { _, _ ->
            viewModel.onEvent(ListWithTasksEvent.OnDeleteClick(taskWithTagAndSubTask))
        }
        setNegativeButton("No") { _, _ -> }
        create()
        show()
    }
}

//@Preview
//@Composable
//fun Preview() {
//    ListWithTasksScreen()
//}