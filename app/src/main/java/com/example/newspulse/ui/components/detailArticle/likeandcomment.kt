package com.example.newspulse.ui.components.detailArticle

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun likeandComment(
    onEmojiReaction: (String) -> Unit
) {
    var selectedReaction by remember { mutableStateOf<String?>(null) }
    val reactions = listOf("👍", "❤️", "😂", "😮", "😢", "😡")

    Spacer(modifier = Modifier.height(20.dp))
    HorizontalDivider(
        modifier = Modifier.padding(horizontal = 24.dp),
        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f)
    )
    Spacer(modifier = Modifier.height(20.dp))
    Row(
        modifier = Modifier.padding(horizontal = 24.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        // Reaction list
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            reactions.forEach { emoji ->
                Text(
                    text = emoji,
                    fontSize = if (selectedReaction == emoji) 28.sp else 22.sp,
                    modifier = Modifier
                        .clickable {
                            val isSelecting = selectedReaction != emoji
                            selectedReaction = if (isSelecting) emoji else null
                            if (isSelecting) {
                                onEmojiReaction(emoji)
                            }
                        }
                        .padding(horizontal = 4.dp)
                )
            }
        }

        Spacer(modifier = Modifier.width(12.dp))
        Text(
            text = "2.4k",
            color = MaterialTheme.colorScheme.onSurface,
            style = MaterialTheme.typography.bodyMedium
        )

        Spacer(modifier = Modifier.weight(1f))

        // Comments
        Icon(
            imageVector = Icons.Default.Chat,
            contentDescription = "Comments",
            tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
            modifier = Modifier.size(28.dp)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = "128",
            color = MaterialTheme.colorScheme.onSurface,
            style = MaterialTheme.typography.bodyMedium
        )
    }
    Spacer(modifier = Modifier.height(140.dp))
}
