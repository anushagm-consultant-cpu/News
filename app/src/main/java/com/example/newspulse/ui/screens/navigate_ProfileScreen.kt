package com.example.newspulse.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForwardIos
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.HelpOutline
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Interests
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.newspulse.navigation.Route


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun ProfileScreen(onNavigate: (Route) -> Unit = {}) {

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(top = 40.dp),
    ) {
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 15.dp)
                    .background(color = Color(0x4AD5CDCD), RoundedCornerShape(10.dp))
                    .height(100.dp)
            ) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        //Articles Read
                        Box(
                            modifier = Modifier
                                .height(95.dp)
                                .width(123.dp)
                                .background(Color.White, RoundedCornerShape(5.dp))
                        ) {

                            Column(
                                modifier = Modifier.align(Alignment.Center),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    text = "1,234",
                                    color = colorResource(id = com.example.newspulse.R.color.teal_700),
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 18.sp
                                )
                                Text(
                                    text = "ARTICLES \n    READ",
                                    fontSize = 13.sp,
                                    lineHeight = 12.sp
                                )
                            }

                        }
                        //Saved Articles
                        Box(
                            modifier = Modifier
                                .height(95.dp)
                                .width(123.dp)
                                .background(Color.White, RoundedCornerShape(5.dp))
                        ) {
                            Column(
                                modifier = Modifier.align(Alignment.Center),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    text = "34",
                                    color = colorResource(id = com.example.newspulse.R.color.teal_700),
                                    fontWeight = FontWeight.Bold, fontSize = 18.sp
                                )
                                Text(
                                    text = "SAVED",
                                    fontSize = 13.sp
                                )
                            }

                        }
                        //Topics
                        Box(
                            modifier = Modifier
                                .height(95.dp)
                                .width(123.dp)
                                .background(Color.White, RoundedCornerShape(5.dp))
                        ) {
                            Column(
                                modifier = Modifier.align(Alignment.Center),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    text = "12",
                                    color = colorResource(id = com.example.newspulse.R.color.teal_700),
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 18.sp
                                )
                                Text(text = "TOPICS", fontSize = 13.sp)
                            }

                        }
                    }

                }

            }
        }
        item {
            Text(
                text = "Account Settings".uppercase(),
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(horizontal = 15.dp, vertical = 10.dp),
                color = colorResource(id = com.example.newspulse.R.color.teal_700)
            )
        }
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 15.dp)
                    .shadow(elevation = 5.dp, shape = RoundedCornerShape(10.dp))

                    .background(color = Color.LightGray, RoundedCornerShape(10.dp))

                    .wrapContentHeight(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(1.dp)
            ) {
                //Box 1 ---Reading history
                Box {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(70.dp)
                            .clip(RoundedCornerShape(topStart = 10.dp, topEnd = 10.dp))
                            .clickable {
                                onNavigate(Route.MyreadingHistroy)

                            }
                            .background(color = Color.White)
                            .padding(horizontal = 20.dp),
                        verticalAlignment = Alignment.CenterVertically

                    ) {
                        Icon(
                            imageVector = Icons.Default.History,
                            contentDescription = "History",
                            modifier = Modifier

                                .size(40.dp)
                                .background(color = Color(0x4AD5CDCD), RoundedCornerShape(8.dp))
                                .padding(8.dp),
                            tint = colorResource(id = com.example.newspulse.R.color.teal_700)
                        )
                        Spacer(modifier = Modifier.width(20.dp))
                        Text(text = "Reading history", fontSize = 18.sp)
                        Spacer(modifier = Modifier.weight(1f))
                        Icon(
                            imageVector = Icons.Default.ArrowForwardIos,
                            contentDescription = "Navigate",
                            modifier = Modifier.size(16.dp),
                            tint = Color.Gray
                        )
                    }
                }
                //Box 2 ----Interst and perfernece
                Box {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(70.dp)
                            .clickable {}
                            .background(color = Color.White)
                            .padding(horizontal = 20.dp),

                        verticalAlignment = Alignment.CenterVertically

                    ) {
                        Icon(
                            imageVector = Icons.Default.Interests,
                            contentDescription = "History",
                            modifier = Modifier

                                .size(40.dp)
                                .background(color = Color(0x4AD5CDCD), RoundedCornerShape(8.dp))
                                .padding(8.dp),
                            tint = colorResource(id = com.example.newspulse.R.color.teal_700)
                        )
                        Spacer(modifier = Modifier.width(20.dp))
                        Text(text = "Interest & Perference", fontSize = 18.sp)
                        Spacer(modifier = Modifier.weight(1f))
                        Icon(
                            imageVector = Icons.Default.ArrowForwardIos,
                            contentDescription = "Navigate",
                            modifier = Modifier.size(16.dp),
                            tint = Color.Gray
                        )
                    }
                }

                //Box 3 ----Notification
                Box {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(70.dp)
                            .clickable {}
                            .background(color = Color.White)
                            .padding(horizontal = 20.dp),

                        verticalAlignment = Alignment.CenterVertically

                    ) {
                        Icon(
                            imageVector = Icons.Default.Notifications,
                            contentDescription = "History",
                            modifier = Modifier

                                .size(40.dp)
                                .background(color = Color(0x4AD5CDCD), RoundedCornerShape(8.dp))
                                .padding(8.dp),
                            tint = colorResource(id = com.example.newspulse.R.color.teal_700)
                        )
                        Spacer(modifier = Modifier.width(20.dp))
                        Text(text = "Notification", fontSize = 18.sp)
                        Spacer(modifier = Modifier.weight(1f))
                        Icon(
                            imageVector = Icons.Default.ArrowForwardIos,
                            contentDescription = "Navigate",
                            modifier = Modifier.size(16.dp),
                            tint = Color.Gray
                        )
                    }
                }

                //box 4 ----- App theme
                Box {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(70.dp)
                            .clickable {}
                            .background(color = Color.White)
                            .padding(horizontal = 20.dp),

                        verticalAlignment = Alignment.CenterVertically

                    ) {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = "History",
                            modifier = Modifier

                                .size(40.dp)
                                .background(color = Color(0x4AD5CDCD), RoundedCornerShape(8.dp))
                                .padding(8.dp),
                            tint = colorResource(id = com.example.newspulse.R.color.teal_700)
                        )
                        Spacer(modifier = Modifier.width(20.dp))
                        Text(text = "App Theme", fontSize = 18.sp)
                        Spacer(modifier = Modifier.weight(1f))
                        Icon(
                            imageVector = Icons.Default.ArrowForwardIos,
                            contentDescription = "Navigate",
                            modifier = Modifier.size(16.dp),
                            tint = Color.Gray
                        )
                    }
                }
                //Box 5   ----Help and support
                Box {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(70.dp)
                            .clickable {}
                            .background(
                                color = Color.White,
                                shape = RoundedCornerShape(bottomStart = 10.dp, bottomEnd = 10.dp)
                            )
                            .padding(horizontal = 20.dp),

                        verticalAlignment = Alignment.CenterVertically

                    ) {
                        Icon(
                            imageVector = Icons.Default.HelpOutline,
                            contentDescription = "History",
                            modifier = Modifier

                                .size(40.dp)
                                .background(color = Color(0x4AD5CDCD), RoundedCornerShape(8.dp))
                                .padding(8.dp),
                            tint = colorResource(id = com.example.newspulse.R.color.teal_700)
                        )
                        Spacer(modifier = Modifier.width(20.dp))
                        Text(text = "Help & Support", fontSize = 18.sp)
                        Spacer(modifier = Modifier.weight(1f))
                        Icon(
                            imageVector = Icons.Default.ArrowForwardIos,
                            contentDescription = "Navigate",
                            modifier = Modifier.size(16.dp),
                            tint = Color.Gray
                        )
                    }
                }

            }
        }
        //Logout button
        item {
            OutlinedButton(
                onClick = {},
                modifier = Modifier.padding(horizontal = 145.dp, vertical = 15.dp),
                border = androidx.compose.foundation.BorderStroke(1.dp, Color(0x6BDA7474))
            ) {
                Text(text = "Log out".uppercase(), color = Color.Red)

            }
        }

    }
}