package com.example.newspulse.ui.components.profileoptionsScreens

import android.content.Intent
import android.net.Uri
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.CoPresent
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun MyHelpScreen(onBackClick: () -> Unit) {
    var searchQuery by remember {
        mutableStateOf("")
    }
    val context = LocalContext.current

    Scaffold(
        topBar = {
            SettingTopAppBar(
                title = "Help & Support",
                onBackClick = onBackClick
            )
        }
    ) { innerPadding ->

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
        ) {
            item {
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = {
                        searchQuery = it
                    },
                    label = {
                        Text("How can we help you?")
                    },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = null
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                )
            }
            item {
                AutoText(
                    text = "TOP QUESTIONS",
                    baseFontSize = 14.sp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                )
                Spacer(modifier = Modifier.height(16.dp))
            }

            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .background(
                            MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f),
                            RoundedCornerShape(10.dp)
                        )
                ) {
                    Column(modifier = Modifier.fillMaxWidth()) {
                        var expandedItem by remember {
                            mutableIntStateOf(-1)
                        }

                        val questions = remember {
                            listOf(
                                HelpQuestion(
                                    "Offline Reading",
                                    "Tap the Save icon on any article. Saved articles can be read without an internet connection."
                                ),
                                HelpQuestion(
                                    "Theme Setting",
                                    "Open Profile > App Theme and choose Light, Dark, Autumn or Fairy."
                                ),
                                HelpQuestion(
                                    "Managing Font Size",
                                    "Go to Profile > Font Size. Here you can upgrade and preview the font size."
                                ),
                                HelpQuestion(
                                    "Troubleshooting Notifications",
                                    "Enable notifications from both your phone settings and the NewsPulse app settings."
                                )
                            )
                        }

                        val filteredQuestions = questions.filter {
                            it.question.contains(searchQuery, ignoreCase = true) ||
                                    it.answer.contains(searchQuery, ignoreCase = true)
                        }

                        if (filteredQuestions.isEmpty()) {
                            Text(
                                text = "No matching questions found.",
                                modifier = Modifier.padding(16.dp),
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        } else {
                            filteredQuestions.forEachIndexed { index, item ->
                                Column {
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .clickable {
                                                expandedItem =
                                                    if (expandedItem == index) -1 else index
                                            }
                                            .padding(16.dp),
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.SpaceBetween
                                    ) {
                                        AutoText(
                                            text = item.question,
                                            baseFontSize = 16.sp,
                                            color = MaterialTheme.colorScheme.onSurface,
                                            fontWeight = FontWeight.SemiBold,
                                            modifier = Modifier.weight(1f)
                                        )

                                        Icon(
                                            imageVector = if (expandedItem == index)
                                                Icons.Default.KeyboardArrowUp
                                            else
                                                Icons.Default.KeyboardArrowDown,
                                            contentDescription = null,
                                            tint = MaterialTheme.colorScheme.primary
                                        )
                                    }

                                    AnimatedVisibility(
                                        visible = expandedItem == index
                                    ) {
                                        AutoText(
                                            text = item.answer,
                                            baseFontSize = 14.sp,
                                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                                            modifier = Modifier
                                                .padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
                                        )
                                    }

                                    if (index != filteredQuestions.size - 1) {
                                        HorizontalDivider(
                                            modifier = Modifier.padding(horizontal = 16.dp),
                                            color = MaterialTheme.colorScheme.outlineVariant
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }

            item {
                Spacer(modifier = Modifier.height(24.dp))
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                ) {
                    AutoText(
                        text = "CONTACT US",
                        baseFontSize = 14.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                ContactBox(
                    icon = Icons.Default.Chat,
                    title = "Chat with us",
                    onClick = {
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://t.me/NewsPulse"))
                        context.startActivity(intent)
                    }
                )

                ContactBox(
                    icon = Icons.Default.Email,
                    title = "Email Support",
                    onClick = {
                        val intent = Intent(Intent.ACTION_SENDTO).apply {
                            data = Uri.parse("mailto:support@newspulse.com")
                            putExtra(Intent.EXTRA_SUBJECT, "Need Help")
                            putExtra(
                                Intent.EXTRA_TEXT,
                                """
                                        Hello NewsPulse Team,
                                        
                                        I need help with:
                                        
                                        Regards,
                                """.trimIndent()
                            )
                        }
                        context.startActivity(intent)
                    }
                )

                ContactBox(
                    icon = Icons.Default.Call,
                    title = "Call",
                    onClick = {
                        val intent = Intent(Intent.ACTION_DIAL).apply {
                            data = Uri.parse("tel:1234567890")
                        }
                        context.startActivity(intent)
                    }

                )
                
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@Composable
fun ContactBox(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    title: String,
    onClick: () -> Unit = {}
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .height(150.dp)
            .background(
                MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f),
                RoundedCornerShape(10.dp)
            )
            .border(
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant),
                shape = RoundedCornerShape(10.dp)
            )
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = icon,
                contentDescription = title,
                modifier = Modifier.size(40.dp),
                tint = MaterialTheme.colorScheme.primary
            )
            AutoText(
                text = title,
                baseFontSize = 18.sp,
                color = MaterialTheme.colorScheme.onSurface,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(top = 8.dp)
            )
        }
    }
}

data class HelpQuestion(
    val question: String,
    val answer: String
)

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun MyHelpScreenPreview() {
    MyHelpScreen(onBackClick = {})
}
