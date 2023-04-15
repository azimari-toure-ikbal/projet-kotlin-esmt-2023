package com.esmt.projet.victodo.core.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun HomeScreen() {
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
                value = "Search...",
                onValueChange = {},
                modifier = Modifier
                    .fillMaxWidth(),
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
            for (i in 0..4) {
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
                                    text = "20",
                                    fontSize = 10.sp,
                                )
                            }
                        }
                        Text(
                            text = "Completedasdasda",
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
            for (i in 0..7) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(55.dp)
                        .padding(bottom = 10.dp, end = 18.dp)
                        .border(
                            1.dp,
                            color = Color(0xFFedf4fe),
                            shape = RoundedCornerShape(10.dp)
                        )
                ) {
                    Text(
                        text = "Work out",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier
                            .padding(start = 15.dp)
                    )
                    Text(
                        text = "20",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier
                            .padding(end = 15.dp)
                    )
                }
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
    }
}

@Preview(showBackground = true)
@Composable
fun Preview() {
    HomeScreen()
}