package com.example.newspulse.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.material.icons.filled.FontDownload
import androidx.compose.material.icons.filled.Headset
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Interests
import androidx.compose.material.icons.filled.HelpOutline
import androidx.compose.material.icons.filled.LocalFireDepartment
import androidx.compose.material.icons.filled.Whatshot
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.newspulse.navigation.Route
import com.example.newspulse.ui.components.profileoptionsScreens.AutoText
import com.example.newspulse.ui.viewmodel.AuthViewModel
import com.example.newspulse.ui.viewmodel.HistoryViewModel
import com.example.newspulse.ui.viewmodel.SavedViewModel

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
    val savedNews by savedViewModel.savedNews.collectAsState()
    val historyNews by historyViewModel.historyNews.collectAsState()
    val readingStreak by historyViewModel.readingStreak.collectAsState()

    // Refactored to call ProfileScreenContent to support Previews
    ProfileScreenContent(
        onNavigate = onNavigate,
        innerPadding = innerPadding,
        isLoggedIn = isLoggedIn,
        savedCount = savedNews.size,
        historyCount = historyNews.size,
        readingStreak = readingStreak,
        onLogoutClick = {
            if (isLoggedIn) {
                authViewModel.logout {
                    onNavigate(Route.Onboarding)
                }
            } else {
                onNavigate(Route.Login)
            }
        }
    )
}

@Composable
fun ProfileScreenContent(
    onNavigate: (Route) -> Unit = {},
    innerPadding: PaddingValues,
    isLoggedIn: Boolean = false,
    savedCount: Int = 0,
    historyCount: Int = 0,
    readingStreak: Int = 0,
    onLogoutClick: () -> Unit = {}
) {
    // Logic for NewsPulse Journey
    val xpPerArticle = 50
    val xpPerLevel = 1000
    val totalXP = historyCount * xpPerArticle
    val currentLevel = (totalXP / xpPerLevel) + 1
    val currentLevelXP = totalXP % xpPerLevel
    val progress = currentLevelXP.toFloat() / xpPerLevel.toFloat()
    val articlesToNextLevel = ((xpPerLevel - currentLevelXP) + xpPerArticle - 1) / xpPerArticle

    val levelNames = listOf(
        "News Novice", "Insight Gatherer", "Info Seeker", "News Enthusiast",
        "Fact Finder", "Daily Digestor", "Curious Reader", "Topic Tracer",
        "Media Maven", "Perspective Pro", "News Navigator", "News Explorer",
        "Journal Junkie", "Headline Hunter", "Story Specialist", "Current Critic",
        "Global Gazer", "Analysis Ace", "Wisdom Weaver", "News Pulse Master"
    )
    val currentLevelName = levelNames.getOrElse(currentLevel - 1) { "News Legend" }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(innerPadding),
        contentPadding = PaddingValues(top = 20.dp),
    ) {
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 15.dp, vertical = 20.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                // Articles Read Card
                ElevatedCard(
                    modifier = Modifier
                        .weight(1f)
                        .height(110.dp)
                        .border(
                            width = 1.dp,
                            color = MaterialTheme.colorScheme.outline.copy(alpha = 0.5f),
                            shape = RoundedCornerShape(40.dp)
                        ),
                    shape = RoundedCornerShape(40.dp),
                    colors = CardDefaults.elevatedCardColors(
                        containerColor = MaterialTheme.colorScheme.surface,
                        contentColor = MaterialTheme.colorScheme.onSurface
                    ),

                ) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        AutoText(
                            text = "$historyCount",
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight.Bold,
                            baseFontSize = 24.sp
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        AutoText(
                            text = "Articles Read",
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            baseFontSize = 12.sp
                        )
                    }
                }

                // Streaks Card
                ElevatedCard(
                    modifier = Modifier
                        .weight(1f)
                        .height(110.dp)
                        .border(
                            width = 1.dp,
                            color = MaterialTheme.colorScheme.outline.copy(alpha = 0.5f),
                            shape = RoundedCornerShape(40.dp)
                        ),
                    shape = RoundedCornerShape(40.dp),
                    colors = CardDefaults.elevatedCardColors(
                        containerColor = MaterialTheme.colorScheme.surface,
                        contentColor = MaterialTheme.colorScheme.onSurface
                    ),

                ) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.Whatshot,
                                contentDescription = "Streak",
                                tint = if (readingStreak > 0) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f),
                                modifier = Modifier.size(24.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            AutoText(
                                text = "$readingStreak",
                                color = MaterialTheme.colorScheme.primary,
                                fontWeight = FontWeight.Bold,
                                baseFontSize = 24.sp
                            )
                        }
                        Spacer(modifier = Modifier.height(4.dp))
                        AutoText(
                            text = "Streaks",
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            baseFontSize = 12.sp
                        )
                    }
                }

                // Saved Articles Card
                ElevatedCard(
                    modifier = Modifier
                        .weight(1f)
                        .height(110.dp)
                        .border(
                            width = 1.dp,
                            color = MaterialTheme.colorScheme.outline.copy(alpha = 0.5f),
                            shape = RoundedCornerShape(40.dp)
                        ),
                    shape = RoundedCornerShape(40.dp),
                    colors = CardDefaults.elevatedCardColors(
                        containerColor = MaterialTheme.colorScheme.surface,
                        contentColor = MaterialTheme.colorScheme.onSurface
                    )
                ) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        AutoText(
                            text = "$savedCount",
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight.Bold,
                            baseFontSize = 24.sp
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        AutoText(
                            text = "Saved",
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            baseFontSize = 12.sp
                        )
                    }
                }
            }
        }


        // ... inside ProfileScreenContent ...

        item {ElevatedCard(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 15.dp, vertical = 10.dp)
                .border(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.outline.copy(alpha = 0.1f),
                    shape = RoundedCornerShape(24.dp)
                )
               ,
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.elevatedCardColors(
                containerColor = MaterialTheme.colorScheme.surface,
                contentColor = MaterialTheme.colorScheme.onSurface
            ),
            elevation = CardDefaults.elevatedCardElevation(
                defaultElevation = 6.dp
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
            ) {
                // Header
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.LocalFireDepartment,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    AutoText(
                        text = "YOUR NEWSPULSE JOURNEY",
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.ExtraBold,
                        baseFontSize = 12.sp,
                    )
                    Spacer(modifier = Modifier.weight(1f))
                }

                Spacer(modifier = Modifier.height(20.dp))

                // Level + Progress
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Level box
                    Box(
                        modifier = Modifier
                            .size(64.dp)
                            .clip(RoundedCornerShape(16.dp))
                            .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.15f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            AutoText(
                                text = "LV",
                                color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.7f),
                                fontWeight = FontWeight.Bold,
                                baseFontSize = 10.sp
                            )
                            AutoText(
                                text = "$currentLevel",
                                color = MaterialTheme.colorScheme.onPrimaryContainer,
                                fontWeight = FontWeight.Bold,
                                baseFontSize = 24.sp
                            )
                        }
                    }

                    Spacer(modifier = Modifier.width(16.dp))

                    Column(modifier = Modifier.weight(1f)) {
                        AutoText(
                            text = currentLevelName,
                            color = MaterialTheme.colorScheme.onSurface,
                            fontWeight = FontWeight.Bold,
                            baseFontSize = 18.sp
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        LinearProgressIndicator(
                            progress = { progress },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(8.dp)
                                .clip(RoundedCornerShape(10.dp)),
                            color = MaterialTheme.colorScheme.primary,
                            trackColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.15f)
                        )

                        Spacer(modifier = Modifier.height(6.dp))

                        AutoText(
                            text = "$currentLevelXP / $xpPerLevel XP",
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            baseFontSize = 12.sp
                        )
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                // Next level
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(12.dp))
                        .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f))
                        .padding(horizontal = 12.dp, vertical = 10.dp)
                        .clickable {
                            onNavigate(Route.Home)
                        },
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.EmojiEvents,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.secondary,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    AutoText(
                        text = "Read $articlesToNextLevel more articles to reach Level ${currentLevel + 1}",
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        baseFontSize = 11.sp
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Icon(
                        imageVector = Icons.Default.ArrowForwardIos,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f),
                        modifier = Modifier.size(12.dp)
                    )
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
                            baseFontSize = 15.sp
                        )
                        Spacer(modifier = Modifier.weight(1f))
                        Icon(
                            imageVector = Icons.Default.ArrowForwardIos,
                            contentDescription = "Navigate",
                            modifier = Modifier.size(15.dp),
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
                            text = "Interest & Preferences",
                            color = MaterialTheme.colorScheme.onSurface,
                            baseFontSize = 15.sp
                        )
                        Spacer(modifier = Modifier.weight(1f))
                        Icon(
                            imageVector = Icons.Default.ArrowForwardIos,
                            contentDescription = "Navigate",
                            modifier = Modifier.size(15.dp),
                            tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f)
                        )
                    }
                }
               // Box 3 ----Listen
                Box {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(70.dp)
                            .clickable { onNavigate(Route.Listen(
                                title = "Listen",
                                content = "",
                                source = "",
                                image = null
                            )) }
                            .background(color = MaterialTheme.colorScheme.surface)
                            .padding(horizontal = 20.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.Headset,
                            contentDescription = "Listen",
                            modifier = Modifier
                                .size(40.dp)
                                .background(color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f), RoundedCornerShape(8.dp))
                                .padding(8.dp),
                            tint = MaterialTheme.colorScheme.primary
                        )
                        Spacer(modifier = Modifier.width(20.dp))
                        AutoText(
                            text = "Listen",
                            color = MaterialTheme.colorScheme.onSurface,
                            baseFontSize = 15.sp
                        )
                        Spacer(modifier = Modifier.weight(1f))
                        Icon(
                            imageVector = Icons.Default.ArrowForwardIos,
                            contentDescription = "Navigate",
                            modifier = Modifier.size(15.dp),
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
                            baseFontSize = 15.sp
                        )
                        Spacer(modifier = Modifier.weight(1f))
                        Icon(
                            imageVector = Icons.Default.ArrowForwardIos,
                            contentDescription = "Navigate",
                            modifier = Modifier.size(15.dp),
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
                            baseFontSize = 15.sp
                        )
                        Spacer(modifier = Modifier.weight(1f))
                        Icon(
                            imageVector = Icons.Default.ArrowForwardIos,
                            contentDescription = "Navigate",
                            modifier = Modifier.size(15.dp),
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
                            baseFontSize = 15.sp
                        )
                        Spacer(modifier = Modifier.weight(1f))
                        Icon(
                            imageVector = Icons.Default.ArrowForwardIos,
                            contentDescription = "Navigate",
                            modifier = Modifier.size(15.dp),
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
                    onClick = onLogoutClick,
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

@Preview(showBackground = true)
@Composable
fun preprofile(){
    // Using ProfileScreenContent in Preview to avoid ViewModel instantiation issues
    ProfileScreenContent(
        onNavigate = {},
        innerPadding = PaddingValues(0.dp),
        isLoggedIn = true,
        savedCount = 5,
        historyCount = 10,
        readingStreak = 3,
        onLogoutClick = {}
    )
}
