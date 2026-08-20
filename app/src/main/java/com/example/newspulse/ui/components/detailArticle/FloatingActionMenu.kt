package com.example.newspulse.ui.components.detailArticle

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.Crossfade
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddReaction
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Headset
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Summarize
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private enum class MenuState {
    Main, Audio, Reactions
}

@Composable
fun FloatingActionMenu(
    modifier: Modifier = Modifier,
    onAiSummaryClick: () -> Unit = {},
    onListenClick: () -> Unit = {},
    onReactClick: (String) -> Unit = {},
    onStopClick: () -> Unit = {},
    isPlaying: Boolean = false,
    onTogglePlay: () -> Unit = {},
    currentProgress: Float = 0f,
    onProgressChange: (Float) -> Unit = {},
    speed: String = "1.0x",
    onSpeedChange: () -> Unit = {}
) {
    var expanded by remember { mutableStateOf(true) }
    var menuState by remember { mutableStateOf(MenuState.Main) }
    val reactions = listOf("👍", "❤️", "😂", "😮", "😢", "😡")

    Surface(
        modifier = modifier.padding(16.dp),
        shape = RoundedCornerShape(16.dp),
        color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.95f),
        tonalElevation = 6.dp,
        shadowElevation = 8.dp
    ) {
        Row(
            modifier = Modifier.padding(4.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            AnimatedVisibility(
                visible = expanded,
                enter = fadeIn() + expandHorizontally(),
                exit = fadeOut() + shrinkHorizontally()
            ) {
                Box(modifier = Modifier.width(IntrinsicSize.Max).height(48.dp)) {
                    // Invisible layer to force width
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier
                            .padding(start = 12.dp, end = 8.dp)
                            .graphicsLayer(alpha = 0f)
                    ) {
                        MenuOption(Icons.Default.Summarize, "AI Summary", {})
                        MenuOption(Icons.Default.Headset, "Listen", {})
                        MenuOption(Icons.Default.AddReaction, "React", {})
                    }

                    Crossfade(
                        targetState = menuState,
                        label = "MenuContent",
                        modifier = Modifier.fillMaxSize()
                    ) { state ->
                        when (state) {
                            MenuState.Audio -> {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .padding(start = 8.dp, end = 8.dp)
                                ) {
                                    IconButton(
                                        onClick = { 
                                            onStopClick()
                                            menuState = MenuState.Main 
                                        },
                                        modifier = Modifier.size(32.dp)
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.Close,
                                            contentDescription = "Stop and Return",
                                            modifier = Modifier.size(18.dp),
                                            tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f)
                                        )
                                    }

                                    Surface(
                                        modifier = Modifier
                                            .size(36.dp)
                                            .clickable { onTogglePlay() },
                                        shape = RoundedCornerShape(8.dp),
                                        color = MaterialTheme.colorScheme.primaryContainer
                                    ) {
                                        Icon(
                                            imageVector = if (isPlaying) Icons.Default.Pause else Icons.Default.PlayArrow,
                                            contentDescription = "Play/Pause",
                                            modifier = Modifier.padding(6.dp),
                                            tint = MaterialTheme.colorScheme.onPrimaryContainer
                                        )
                                    }

                                    Slider(
                                        value = currentProgress,
                                        onValueChange = onProgressChange,
                                        modifier = Modifier.weight(1f),
                                        colors = SliderDefaults.colors(
                                            thumbColor = MaterialTheme.colorScheme.primary,
                                            activeTrackColor = MaterialTheme.colorScheme.primary,
                                            inactiveTrackColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.2f)
                                        )
                                    )

                                    Surface(
                                        modifier = Modifier
                                            .height(36.dp)
                                            .clickable { onSpeedChange() },
                                        shape = RoundedCornerShape(8.dp),
                                        color = MaterialTheme.colorScheme.secondaryContainer
                                    ) {
                                        Box(
                                            modifier = Modifier.padding(horizontal = 8.dp),
                                            contentAlignment = Alignment.Center
                                        ) {
                                            Text(
                                                text = speed,
                                                style = MaterialTheme.typography.labelMedium,
                                                color = MaterialTheme.colorScheme.onSecondaryContainer
                                            )
                                        }
                                    }
                                }
                            }
                            MenuState.Reactions -> {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceEvenly,
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .padding(horizontal = 8.dp)
                                ) {
                                    IconButton(
                                        onClick = { menuState = MenuState.Main },
                                        modifier = Modifier.size(32.dp)
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.Close,
                                            contentDescription = "Return",
                                            modifier = Modifier.size(18.dp),
                                            tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f)
                                        )
                                    }
                                    reactions.forEach { emoji ->
                                        Text(
                                            text = emoji,
                                            fontSize = 22.sp,
                                            modifier = Modifier
                                                .clickable {
                                                    onReactClick(emoji)
                                                    menuState = MenuState.Main
                                                }
                                                .padding(horizontal = 4.dp)
                                        )
                                    }
                                }
                            }
                            MenuState.Main -> {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .padding(start = 12.dp, end = 8.dp)
                                ) {
                                    MenuOption(
                                        icon = Icons.Default.Summarize,
                                        label = "AI Summary",
                                        onClick = onAiSummaryClick
                                    )
                                    MenuOption(
                                        icon = Icons.Default.Headset,
                                        label = "Listen",
                                        onClick = {
                                            onListenClick()
                                            menuState = MenuState.Audio
                                        }
                                    )
                                    MenuOption(
                                        icon = Icons.Default.AddReaction,
                                        label = "React",
                                        onClick = { menuState = MenuState.Reactions }
                                    )
                                }
                            }
                        }
                    }
                }
            }

            Surface(
                modifier = Modifier
                    .size(48.dp)
                    .clickable {
                        expanded = !expanded
                        if (!expanded) menuState = MenuState.Main
                    },
                shape = RoundedCornerShape(12.dp),
                color = MaterialTheme.colorScheme.primary
            ) {
                Icon(
                    imageVector = Icons.Default.AutoAwesome,
                    contentDescription = if (expanded) "Hide options" else "Show options",
                    modifier = Modifier.padding(12.dp),
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }
        }
    }
}

@Composable
fun MenuOption(
    icon: ImageVector,
    label: String,
    onClick: () -> Unit
) {
    Surface(
        modifier = Modifier.clickable { onClick() },
        shape = RoundedCornerShape(12.dp),
        color = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.7f)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = label,
                modifier = Modifier.size(20.dp),
                tint = MaterialTheme.colorScheme.onSecondaryContainer
            )
            Text(
                text = label,
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSecondaryContainer,
                fontSize = 12.sp
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FloatingActionMenuPreview() {
    MaterialTheme {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomEnd) {
            FloatingActionMenu()
        }
    }
}
