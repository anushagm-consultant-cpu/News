package com.example.newspulse.ui.screens

import androidx.compose.foundation.BorderStroke
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
import androidx.compose.material.icons.filled.FontDownload
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.newspulse.navigation.Route
import com.example.newspulse.ui.components.profileoptionsScreens.AutoText
import com.example.newspulse.ui.viewmodel.AuthViewModel
import com.example.newspulse.ui.viewmodel.HistoryViewModel
import com.example.newspulse.ui.viewmodel.SavedViewModel
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Interests
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.HelpOutline

@Composable
fun ProfileScreen(
    onNavigate: (Route) -> Unit = {},
    onLogout: () -> Unit = {},
    innerPadding: PaddingValues,
    savedViewModel: SavedViewModel = hiltViewModel(),
    historyViewModel: HistoryViewModel = hiltViewModel(),
    authViewModel: AuthViewModel = hiltViewModel()
) {
    val isLoggedIn = authViewModel.isLoggedIn
    val savedCount by savedViewModel.savedNews.collectAsState()

    val historyCount by historyViewModel.historyNews.collectAsState()
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(innerPadding),
        contentPadding = PaddingValues(top = 40.dp),
    ) {
        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 15.dp)
                    .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f), RoundedCornerShape(10.dp))
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
                                .background(MaterialTheme.colorScheme.surface, RoundedCornerShape(5.dp))
                        ) {
                            Column(
                                modifier = Modifier.align(Alignment.Center),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                AutoText(
                                    text ="${ historyCount.size}",
                                    color = MaterialTheme.colorScheme.primary,
                                    fontWeight = FontWeight.Bold,
                                    baseFontSize = 18.sp
                                )
                                AutoText(
                                    text = "ARTICLES \n    READ",
                                    color = MaterialTheme.colorScheme.onSurface,
                                    baseFontSize = 13.sp,
                                    lineHeight = 12.sp
                                )
                            }
                        }
                        //Saved Articles
                        Box(
                            modifier = Modifier
                                .height(95.dp)
                                .width(123.dp)
                                .background(MaterialTheme.colorScheme.surface, RoundedCornerShape(5.dp))
                        ) {
                            Column(
                                modifier = Modifier.align(Alignment.Center),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                AutoText(
                                    text = "${savedCount.size}",
                                    color = MaterialTheme.colorScheme.primary,
                                    fontWeight = FontWeight.Bold, baseFontSize = 18.sp
                                )
                                AutoText(
                                    text = "SAVED",
                                    color = MaterialTheme.colorScheme.onSurface,
                                    baseFontSize = 13.sp
                                )
                            }
                        }
                        //Topics
                        Box(
                            modifier = Modifier
                                .height(95.dp)
                                .width(123.dp)
                                .background(MaterialTheme.colorScheme.surface, RoundedCornerShape(5.dp))
                        ) {
                            Column(
                                modifier = Modifier.align(Alignment.Center),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                AutoText(
                                    text = "12",
                                    color = MaterialTheme.colorScheme.primary,
                                    fontWeight = FontWeight.Bold,
                                    baseFontSize = 18.sp
                                )
                                AutoText(
                                    text = "TOPICS",
                                    color = MaterialTheme.colorScheme.onSurface,
                                    baseFontSize = 13.sp
                                )
                            }
                        }
                    }
                }
            }
        }
        item {
            AutoText(
                text = "Account Settings".uppercase(),
                baseFontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(horizontal = 15.dp, vertical = 10.dp),
                color = MaterialTheme.colorScheme.primary
            )
        }
        item {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 15.dp)
                    .shadow(elevation = 5.dp, shape = RoundedCornerShape(10.dp))
                    .background(MaterialTheme.colorScheme.outlineVariant, RoundedCornerShape(10.dp))
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
                            .clickable { onNavigate(Route.MyreadingHistroy) }
                            .background(color = MaterialTheme.colorScheme.surface)
                            .padding(horizontal = 20.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.History,
                            contentDescription = "History",
                            modifier = Modifier
                                .size(40.dp)
                                .background(color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f), RoundedCornerShape(8.dp))
                                .padding(8.dp),
                            tint = MaterialTheme.colorScheme.primary
                        )
                        Spacer(modifier = Modifier.width(20.dp))
                        AutoText(
                            text = "Reading history", 
                            color = MaterialTheme.colorScheme.onSurface,
                            baseFontSize = 18.sp
                        )
                        Spacer(modifier = Modifier.weight(1f))
                        Icon(
                            imageVector = Icons.Default.ArrowForwardIos,
                            contentDescription = "Navigate",
                            modifier = Modifier.size(16.dp),
                            tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f)
                        )
                    }
                }
                //Box 2 ----Interst and perfernece
                Box {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(70.dp)
                            .clickable { onNavigate(Route.MyInterestAndPreference) }
                            .background(color = MaterialTheme.colorScheme.surface)
                            .padding(horizontal = 20.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Interests,
                            contentDescription = "History",
                            modifier = Modifier
                                .size(40.dp)
                                .background(color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f), RoundedCornerShape(8.dp))
                                .padding(8.dp),
                            tint = MaterialTheme.colorScheme.primary
                        )
                        Spacer(modifier = Modifier.width(20.dp))
                        AutoText(
                            text = "Interest & Perference", 
                            color = MaterialTheme.colorScheme.onSurface,
                            baseFontSize = 18.sp
                        )
                        Spacer(modifier = Modifier.weight(1f))
                        Icon(
                            imageVector = Icons.Default.ArrowForwardIos,
                            contentDescription = "Navigate",
                            modifier = Modifier.size(16.dp),
                            tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f)
                        )
                    }
                }
                //Box 3 ----Notification
                Box {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(70.dp)
                            .clickable { onNavigate(Route.MyrNotificationScreen) }
                            .background(color = MaterialTheme.colorScheme.surface)
                            .padding(horizontal = 20.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Notifications,
                            contentDescription = "History",
                            modifier = Modifier
                                .size(40.dp)
                                .background(color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f), RoundedCornerShape(8.dp))
                                .padding(8.dp),
                            tint = MaterialTheme.colorScheme.primary
                        )
                        Spacer(modifier = Modifier.width(20.dp))
                        AutoText(
                            text = "Notification", 
                            color = MaterialTheme.colorScheme.onSurface,
                            baseFontSize = 18.sp
                        )
                        Spacer(modifier = Modifier.weight(1f))
                        Icon(
                            imageVector = Icons.Default.ArrowForwardIos,
                            contentDescription = "Navigate",
                            modifier = Modifier.size(16.dp),
                            tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f)
                        )
                    }
                }
                //box 4 ----- App theme
                Box {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(70.dp)
                            .clickable { onNavigate(Route.MyAppThemeScreen) }
                            .background(color = MaterialTheme.colorScheme.surface)
                            .padding(horizontal = 20.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = "History",
                            modifier = Modifier
                                .size(40.dp)
                                .background(color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f), RoundedCornerShape(8.dp))
                                .padding(8.dp),
                            tint = MaterialTheme.colorScheme.primary
                        )
                        Spacer(modifier = Modifier.width(20.dp))
                        AutoText(
                            text = "App Theme", 
                            color = MaterialTheme.colorScheme.onSurface,
                            baseFontSize = 18.sp
                        )
                        Spacer(modifier = Modifier.weight(1f))
                        Icon(
                            imageVector = Icons.Default.ArrowForwardIos,
                            contentDescription = "Navigate",
                            modifier = Modifier.size(16.dp),
                            tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f)
                        )
                    }
                }
//font size customize
                Box {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(70.dp)
                            .clickable { onNavigate(Route.FontSizeScreen) }
                            .background(color = MaterialTheme.colorScheme.surface)
                            .padding(horizontal = 20.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.FontDownload,
                            contentDescription = "History",
                            modifier = Modifier
                                .size(40.dp)
                                .background(color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f), RoundedCornerShape(8.dp))
                                .padding(8.dp),
                            tint = MaterialTheme.colorScheme.primary
                        )
                        Spacer(modifier = Modifier.width(20.dp))
                        AutoText(
                            text = "Font Size",
                            color = MaterialTheme.colorScheme.onSurface,
                            baseFontSize = 18.sp
                        )
                        Spacer(modifier = Modifier.weight(1f))
                        Icon(
                            imageVector = Icons.Default.ArrowForwardIos,
                            contentDescription = "Navigate",
                            modifier = Modifier.size(16.dp),
                            tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f)
                        )
                    }
                }
                //Box 5   ----Help and support
                Box {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(70.dp)
                            .clip(RoundedCornerShape(bottomStart = 10.dp, bottomEnd = 10.dp))
                            .clickable { onNavigate(Route.MyHelpScreen) }
                            .background(color = MaterialTheme.colorScheme.surface)
                            .padding(horizontal = 20.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.HelpOutline,
                            contentDescription = "History",
                            modifier = Modifier
                                .size(40.dp)
                                .background(color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f), RoundedCornerShape(8.dp))
                                .padding(8.dp),
                            tint = MaterialTheme.colorScheme.primary
                        )
                        Spacer(modifier = Modifier.width(20.dp))
                        AutoText(
                            text = "Help & Support", 
                            color = MaterialTheme.colorScheme.onSurface,
                            baseFontSize = 18.sp
                        )
                        Spacer(modifier = Modifier.weight(1f))
                        Icon(
                            imageVector = Icons.Default.ArrowForwardIos,
                            contentDescription = "Navigate",
                            modifier = Modifier.size(16.dp),
                            tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f)
                        )
                    }
                }
            }
        }
        //Logout button
        item {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.BottomCenter
            ) {
                OutlinedButton(
                    onClick = {
                       if(isLoggedIn){
                           authViewModel.logout {
                               onNavigate(Route.Onboarding)
                           }

                       }else{
                           onNavigate(Route.Login)

                       }
                    },
                    modifier = Modifier.padding(vertical = 15.dp),
                    border = BorderStroke(1.dp, if(isLoggedIn) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primary),


                ) {
                    AutoText(text = if(isLoggedIn) "Logout" else "Login"
                        .uppercase(),
                        color =if(isLoggedIn){
                            MaterialTheme.colorScheme.error
                        }else{
                            MaterialTheme.colorScheme.primary
                        },
                        baseFontSize = 16.sp)
                }
            }
        }
    }
}
