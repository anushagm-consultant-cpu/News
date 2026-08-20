package com.example.newspulse.ui.components.detailArticle

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AlarmOff
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Report
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.newspulse.ui.components.profileoptionsScreens.AutoText

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArticleDetailTopBar(
    isSaved: Boolean,
    bounceTrigger: Int = 0,
    topic: String = "this topic",
    onBackClick: () -> Unit,
    onShareClick: () -> Unit,
    onSaveClick: () -> Unit,
    onMuteClick: () -> Unit
) {
    var showMenu by remember { mutableStateOf(false) }
    val scale = remember { Animatable(1f) }

    LaunchedEffect(bounceTrigger) {
        if (bounceTrigger > 0) {
            scale.animateTo(
                targetValue = 1.3f,
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessLow
                )
            )
            scale.animateTo(
                targetValue = 1f,
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessLow
                )
            )
        }
    }

    TopAppBar(
        title = { Text(text = "") },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.Transparent,
            scrolledContainerColor = Color.Transparent
        ),
        navigationIcon = {
            IconButton(onClick = onBackClick) {
                Icon(
                    imageVector = Icons.Default.ArrowBackIosNew,
                    contentDescription = "Back"
                )
            }
        },
        actions = {
            // Icons on the right side
            IconButton(onClick = onShareClick) {
                Icon(
                    imageVector = Icons.Default.Share,
                    contentDescription = "Share"
                )
            }
            IconButton(
                onClick = onSaveClick,
                modifier = Modifier.scale(scale.value)
            ) {
                Icon(
                    imageVector = if (isSaved) {
                        Icons.Filled.Bookmark
                    } else {
                        Icons.Default.BookmarkBorder
                    },
                    contentDescription = "Save"
                )
            }
            Box {
                IconButton(onClick = { showMenu = true }) {
                    Icon(
                        imageVector = Icons.Default.MoreVert,
                        contentDescription = "More"
                    )
                }
                DropdownMenu(
                    expanded = showMenu,
                    onDismissRequest = { showMenu = false }

                ) {
                    DropdownMenuItem(
                        text = {
                            AutoText(text="Not Interested",
                                baseFontSize = 16.sp,
                                fontWeight = FontWeight.SemiBold) },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.VisibilityOff,
                                contentDescription = null
                            )
                        },
                        onClick = {


                        }
                    )
                    DropdownMenuItem(
                        text = {
                            AutoText(text="Mute Article",
                                baseFontSize = 16.sp,
                                fontWeight = FontWeight.SemiBold) },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.AlarmOff,
                                contentDescription = null
                            )
                        },
                        onClick = {
                            showMenu = false
                            onMuteClick()
                        }
                    )
                    DropdownMenuItem(
                        text = {
                            AutoText(text="Report Article",
                                baseFontSize = 16.sp,
                                fontWeight = FontWeight.SemiBold) },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Report,
                                contentDescription = null
                            )
                        },
                        onClick = {
                        }
                    )

                }
                }

        },
    )
}
