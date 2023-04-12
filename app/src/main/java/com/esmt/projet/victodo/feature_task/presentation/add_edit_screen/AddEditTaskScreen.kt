package com.esmt.projet.victodo.feature_task.presentation.add_edit_screen

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.esmt.projet.victodo.core.presentation.components.AddEditHeader

@Composable
fun AddEditTaskScreen() {
    AddEditHeader(title = "New Task") {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Title(title = "Title")
            OutlinedTextField(
                value = "Test",
                onValueChange = {},
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
            // Dropdown menu
            Drop()
            Spacer(modifier = Modifier.height(24.dp))
            Title(title = "Description")
            OutlinedTextField(
                value = "",
                onValueChange = {},
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
                Title(title = "Priority")
            }
            Spacer(modifier = Modifier.height(24.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(55.dp)
                    .border(1.5.dp, Color(0xFFedf4fe), RoundedCornerShape(10.dp))
            ) {
                Icon(
                    imageVector = Icons.Default.DateRange,
                    contentDescription = null,
                    tint = Color.Blue,
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
                        text = "Sunday, 9 March 2023",
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
            ) {
                Icon(
                    imageVector = Icons.Default.Info,
                    contentDescription = null,
                    tint = Color.Blue,
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
                        text = "Sunday, 9 March 2023",
                        color = Color(0xFF006EE9),
                        fontSize = 10.sp,
                    )
                }
            }
            Spacer(modifier = Modifier.height(24.dp))
            DropRepeat()

            Spacer(modifier = Modifier.height(24.dp))
            EndRepeat()

            Spacer(modifier = Modifier.height(24.dp))
            Title(title = "Tags")
            Spacer(modifier = Modifier.height(8.dp))
            EndRepeat()

            Spacer(modifier = Modifier.height(24.dp))
            Title(title = "Priority")
            Spacer(modifier = Modifier.height(8.dp))
            EndRepeat()

            Spacer(modifier = Modifier.height(24.dp))
            Title(title = "Tags")
            OutlinedTextField(
                value = "0",
                onValueChange = {},
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

        }
    }
}

@Composable
fun Drop() {
    var expanded by remember { mutableStateOf(false) }
    var selectedItem by remember { mutableStateOf("Option 1") }
    val options = listOf("Option 1", "Option 2", "Option 3")
//    val triangleIcon: ImageVector = if (expanded) vectorResource(id = R.drawable.drop_up) else vectorResource(id = R.drawable.drop_down)
    val triangleIcon: ImageVector = if (expanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(MaterialTheme.shapes.medium)
            .background(Color.Transparent)
            .border(1.5.dp, Color(0xFFedf4fe), RoundedCornerShape(10.dp))
            .clickable(onClick = { expanded = !expanded })
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = selectedItem,
            color = Color.DarkGray,
            modifier = Modifier.weight(1f)
        )
        Icon(
            imageVector = triangleIcon,
            contentDescription = "Dropdown icon",
            tint = Color.Blue
        )
    }
    DropdownMenu(
        expanded = expanded,
        onDismissRequest = { expanded = false },
        modifier = Modifier.fillMaxWidth()
    ) {
        options.forEach { option ->
            DropdownMenuItem(onClick = {
                selectedItem = option
                expanded = false
            }) {
                Text(text = option)
            }
        }
    }
}

@Composable
fun DropRepeat() {
    var expanded by remember { mutableStateOf(false) }
    var selectedItem by remember { mutableStateOf("Option 1") }
    val options = listOf("Option 1", "Option 2", "Option 3")
//    val triangleIcon: ImageVector = if (expanded) vectorResource(id = R.drawable.drop_up) else vectorResource(id = R.drawable.drop_down)
    val triangleIcon: ImageVector = if (expanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(55.dp)
            .clip(MaterialTheme.shapes.medium)
            .background(Color.Transparent)
            .border(1.5.dp, Color(0xFFedf4fe), RoundedCornerShape(10.dp))
            .clickable(onClick = { expanded = !expanded })
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Default.Refresh,
            contentDescription = null,
            tint = Color.Blue
        )
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(8.dp)
                .align(Alignment.CenterVertically)
        ) {
            Text(
                text = "Repeat",
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
        expanded = expanded,
        onDismissRequest = { expanded = false },
        modifier = Modifier.fillMaxWidth()
    ) {
        options.forEach { option ->
            DropdownMenuItem(onClick = {
                selectedItem = option
                expanded = false
            }) {
                Text(text = option)
            }
        }
    }
}

@Composable
fun EndRepeat() {
    var expanded by remember { mutableStateOf(false) }
    var selectedItem by remember { mutableStateOf("Option 1") }
    val options = listOf("Option 1", "Option 2", "Option 3")
//    val triangleIcon: ImageVector = if (expanded) vectorResource(id = R.drawable.drop_up) else vectorResource(id = R.drawable.drop_down)
    val triangleIcon: ImageVector = if (expanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(55.dp)
            .clip(MaterialTheme.shapes.medium)
            .background(Color.Transparent)
            .border(1.5.dp, Color(0xFFedf4fe), RoundedCornerShape(10.dp))
            .clickable(onClick = { expanded = !expanded })
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "End Repeat",
            color = Color.DarkGray,
            modifier = Modifier.weight(1f)
        )
        Icon(
            imageVector = triangleIcon,
            contentDescription = "Dropdown icon",
            tint = Color.Blue
        )
    }
    DropdownMenu(
        expanded = expanded,
        onDismissRequest = { expanded = false },
        modifier = Modifier.fillMaxWidth()
    ) {
        options.forEach { option ->
            DropdownMenuItem(onClick = {
                selectedItem = option
                expanded = false
            }) {
                Text(text = option)
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

@Preview
@Composable
fun Preview() {
    AddEditTaskScreen()
}