package com.esmt.projet.victodo.core.presentation

import android.app.AlertDialog
import android.content.Context
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.esmt.projet.victodo.core.presentation.components.DropDownItem
import com.esmt.projet.victodo.core.presentation.components.TaskListItem
import com.esmt.projet.victodo.core.presentation.util.Screen
import com.esmt.projet.victodo.feature_list.domain.model.TaskList
import com.esmt.projet.victodo.feature_list.domain.model.TaskListWithTasksAndTagsSubTasks

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeScreenViewModel = hiltViewModel(),
) {
    val state = viewModel.state.value
    val searchFieldState = viewModel.searchFieldState.value
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(top = 32.dp, start = 8.dp, end = 8.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            TextField(
                value = searchFieldState.hint,
                onValueChange = {
                    viewModel.onEvent(HomeScreenEvent.onSearch(it))
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .onFocusChanged {
                        viewModel.onEvent(HomeScreenEvent.onSearchFocusChanged(it))
                    },
                singleLine = true,
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    backgroundColor = Color(0xFFEEF5FD),
                    focusedBorderColor = Color.Transparent,
                    unfocusedBorderColor = Color.Transparent,
                    cursorColor = Color(0xFFEEF5FD),
                    textColor = Color.DarkGray,
                ),
                shape = RoundedCornerShape(24.dp),
            )
        }
        Spacer(modifier = Modifier.height(32.dp))
        FlowRow(
            maxItemsInEachRow = 3,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 12.dp)
        ) {
            for (taskList in state.listOfPinnedList) {
                Box(
                    modifier = Modifier
                        .padding(start = 8.dp, end = 8.dp, bottom = 12.dp)
                        .height(100.dp)
                        .width(100.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color.Blue)
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.Red)
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.End,
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 4.dp, end = 12.dp)
                        ) {
                            Box(
                                contentAlignment = Alignment.TopCenter,
                                modifier = Modifier
                                    .width(50.dp)
                                    .clip(RoundedCornerShape(10.dp))
                                    .background(Color.White)
                            ) {
                                Text(
                                    text = taskList.tasks.size.toString(),
                                    fontSize = 10.sp,
                                )
                            }
                        }
                        Text(
                            text = taskList.taskList.title,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Color.White,
                            modifier = Modifier
                                .padding(top = 30.dp, start = 2.dp, end = 2.dp)
                        )
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(52.dp))
        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(
                text = "My Lists",
                fontWeight = FontWeight.SemiBold,
                fontSize = 20.sp,
                modifier = Modifier
                    .padding(bottom = 12.dp)
            )
            for (taskList in state.listOfTaskList) {
                TaskListItem(
                    taskList = taskList,
                    dropDownItems = listOf(
                    DropDownItem(1, "Edit"),
                    DropDownItem(2, "Delete")
                    ),
                    onItemClick = {
                        when(it.id){
                            1-> {
                                navController.navigate(Screen.AddEditListScreen.route + "/${taskList.taskList.id}")
                            }
                            2-> {
                                confirmDeleteList(
                                    context = context,
                                    taskList = taskList,
                                    viewModel = viewModel
                                )
                            }
                        }
                    }
                )
            }
        }
        Spacer(modifier = Modifier.height(40.dp))
        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(
                    text = "Tags",
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 20.sp,
                    modifier = Modifier
                        .padding(bottom = 12.dp)
                )
                Text(text = ">")
            }
            FlowRow(
                maxItemsInEachRow = 4,
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                for (i in 0..6) {
                    Box(
                        modifier = Modifier
                            .padding(8.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .background(Color(0xFFEEF5FD))
                            .border(
                                1.dp,
                                color = Color(0xFFedf4fe),
                                shape = RoundedCornerShape(10.dp)
                            )
                    ) {
                        Text(
                            text = "# All tags",
                            fontSize = 14.sp,
                            modifier = Modifier
                                .padding(4.dp)
                        )
                    }

                }
            }
        }
        Spacer(modifier = Modifier.height(40.dp))
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Box(
                modifier = Modifier
                    .height(35.dp)
                    .width(90.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(Color(0xFF006EE9))
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(start = 8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = null,
                        tint = Color.White,
                    )
                    Text(
                        text = "New List",
                        fontSize = 12.sp,
                        color = Color.White,
                    )
                }
            }

            Box(
                modifier = Modifier
                    .height(35.dp)
                    .width(90.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(Color(0xFFEEF5FD))
                    .clickable {
                        navController.navigate(Screen.AddEditListScreen.route)
                    }
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(start = 8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = null,
                        tint = Color(0xFF006EE9),
                    )
                    Text(
                        text = "New List",
                        fontSize = 12.sp,
                        color = Color(0xFF006EE9),
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(24.dp))
    }
}

@Preview(showBackground = true)
@Composable
fun Preview() {
    HomeScreen(navController = rememberNavController())
}

fun getTaskListItems(): List<TaskList> {
    return listOf(
        TaskList(
            id = 1,
            title = "Work out",
        ),
        TaskList(
            id = 2,
            title = "Work out2",
        ),
        TaskList(
            id = 3,
            title = "Work out3",
        ),
    )
}

fun confirmDeleteList(context: Context, taskList: TaskListWithTasksAndTagsSubTasks, viewModel: HomeScreenViewModel){
    val builder = AlertDialog.Builder(context)
    builder.apply {
        setTitle("Supprimer la liste")
        setMessage("Voulez-vous vraiment supprimer la liste ${taskList.taskList.title} ?")
        setPositiveButton("Oui"){ _, _ ->
            viewModel.onEvent(HomeScreenEvent.onSupprimerClicked(taskList))
        }
        setNegativeButton("Non"){ _, _ ->

        }
    }
}