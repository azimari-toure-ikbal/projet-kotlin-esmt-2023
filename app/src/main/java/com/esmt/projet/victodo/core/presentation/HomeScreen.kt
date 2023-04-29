package com.esmt.projet.victodo.core.presentation

import android.app.AlertDialog
import android.content.Context
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowRight
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
                value = if (searchFieldState.isHintVisible) searchFieldState.hint
                else searchFieldState.searchQuery,
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
                        .clickable {
                            navController.navigate(
                                route = Screen.ListWithTasksScreen.route
                                        +"/${taskList.taskList.id}"
                                        +"/${taskList.taskList.title}"
                                        +"/${taskList.taskList.icon}"
                            )
                        }
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
                            1L-> {
                                navController.navigate(
                                    route = Screen.AddEditListScreen.route
                                            +"?listId=${taskList.taskList.id}"
                                            +"&listColor=${taskList.taskList.color}"
                                            +"&listTitle=${taskList.taskList.title}"
                                )
                            }
                            2L-> {
                                confirmDeleteList(
                                    context = context,
                                    taskList = taskList,
                                    viewModel = viewModel
                                )
                            }
                        }
                    },
                    onListClick = {
                        navController.navigate(
                            route = Screen.ListWithTasksScreen.route
                                    +"/${taskList.taskList.id}"
                                    +"/${taskList.taskList.title}"
                                    +"/${taskList.taskList.icon}"
                        )
                    },
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
                IconButton(
                    onClick = {
                        viewModel.onEvent(HomeScreenEvent.onTagRevealClicked)
                    },
                ) {
                    Icon(
                        imageVector = if (state.isTagRevealed) Icons.Default.KeyboardArrowRight
                        else Icons.Default.KeyboardArrowDown,
                        contentDescription = "Reveal Tags",
                        tint = Color(0xFF3F3F3F)
                    )
                }
            }
            AnimatedVisibility(
                visible = state.isTagRevealed,
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                FlowRow(
                    maxItemsInEachRow = 4,
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    for (tag in state.listOfTags) {
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
                                text = tag.title,
                                fontSize = 14.sp,
                                modifier = Modifier
                                    .padding(4.dp)
                            )
                        }

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
                        text = "New Task",
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
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(start = 8.dp)
                        .clickable {
                            navController.navigate(Screen.AddEditListScreen.route)
                        }
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
    }
}

@Preview(showBackground = true)
@Composable
fun Preview() {
    HomeScreen(navController = rememberNavController())
}

fun confirmDeleteList(context: Context, taskList: TaskListWithTasksAndTagsSubTasks, viewModel: HomeScreenViewModel){
    val builder = AlertDialog.Builder(context)
    builder.apply {
        setTitle("Supprimer la liste")
        setMessage("Voulez-vous vraiment supprimer la liste ${taskList.taskList.title} ?")
        setPositiveButton("Oui"){ _, _ ->
            viewModel.onEvent(HomeScreenEvent.onSupprimerClicked(taskList))
        }
        setNegativeButton("Non"){ dialog, _ ->
            dialog.dismiss()
        }
    }
    builder.create().show()
}