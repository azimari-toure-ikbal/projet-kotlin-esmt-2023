package com.esmt.projet.victodo.feature_list.presentation.add_edit_screen

import androidx.compose.animation.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.esmt.projet.victodo.core.presentation.components.AddEditHeader
import com.esmt.projet.victodo.feature_list.domain.model.TaskList
import com.esmt.projet.victodo.feature_list.presentation.components.CircleColorPicker
import com.esmt.projet.victodo.feature_list.presentation.components.CircleEmoticonPicker
import kotlinx.coroutines.launch

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun AddEditListScreen(
    navController: NavController,
    listColor: Int,
    viewModel: AddEditViewModel = hiltViewModel()

) {

    val titleState = viewModel.listTitle.value
    val colorState = viewModel.listColor.value
    val iconState = viewModel.listIcon.value

    val scaffoldState = rememberScaffoldState()

    val listBackgroundColorAnimatable = remember {
        Animatable(
            Color(if(listColor != -1) listColor else viewModel.listColor.value)
        )
    }

    val scope = rememberCoroutineScope()

    AddEditHeader(title = "New List") {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp)
        ) {
            Box(
                Modifier
                    .size(72.dp)
                    .clip(CircleShape)
                    .background(listBackgroundColorAnimatable.value)
//                    .background(Color(0xFFCB2F2F))
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add",
                    tint = Color.White,
                    modifier = Modifier
                        .size(42.dp)
                        .align(Alignment.Center)
                )
            }
            Spacer(modifier = Modifier.height(32.dp))
            TextField(
                value = "List Name",
                onValueChange = {
                    viewModel.onEvent(AddEditListsEvent.EnteredTitle(it))
                },
                singleLine = true,
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color(0xFFEEF5FD),
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    cursorColor = Color.Black,
                    textColor = Color.Black,
                ),
                shape = RoundedCornerShape(10.dp),
                textStyle = TextStyle(textAlign = TextAlign.Center),
            )
            Spacer(modifier = Modifier.height(32.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(10.dp))
                    .background(color = Color(0xFFEEF5FD))
            ) {
                Column() {
                    FlowRow(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        maxItemsInEachRow = 6,
                    ) {
                        TaskList.listColors.forEach {color ->
                            Box(
                                modifier = Modifier
                                    .size(40.dp)
                                    .clip(CircleShape)
                                    .background(color = Color(color.toArgb()))
                                    .clickable {
                                        scope.launch {
                                            listBackgroundColorAnimatable.animateTo(
                                                targetValue = Color(color.toArgb()),
                                                animationSpec = tween(durationMillis = 500)
                                            )
                                        }
                                        viewModel.onEvent(AddEditListsEvent.SelectedColor(color.toArgb()))
                                    }
                            )
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(32.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(10.dp))
                    .background(color = Color(0xFFEEF5FD))
            ) {
                Column {
                    FlowRow(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        maxItemsInEachRow = 6,
                    ) {
                        TaskList.listIcons.forEach {color ->
                            Box(
                                modifier = Modifier
                                    .size(40.dp)
                                    .clip(CircleShape)
                                    .clickable {
                                        viewModel.onEvent(AddEditListsEvent.SelectedIcon(iconState))
                                    }
                            ) {
                                Image(painter = painterResource(id = iconState.toInt()), contentDescription = null )
                            }
                        }
                    }

                    FlowRow(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        maxItemsInEachRow = 6,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                    ) {
                       CircleEmoticonPicker()
                    }
                }
            }
            Spacer(modifier = Modifier.height(64.dp))
            Button(
                onClick = { /*TODO*/ },
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color(0xFF006EE9),
                    contentColor = Color.White,
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
            ) {
                Text(text = "Create List")
            }
        }
    }
}

//@Preview
//@Composable
//fun Preview() {
//    AddEditListScreen()
//}