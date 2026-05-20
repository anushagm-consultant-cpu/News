package com.example.newspulse.ui.components.detailArticle

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.HeartBroken
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun likeandComment(){
    var islike by remember { mutableStateOf(false) }
    HorizontalDivider(modifier = Modifier.padding(horizontal = 24.dp))
    Row(
        verticalAlignment = androidx.compose.ui.Alignment.CenterVertically,
    ) {
        Icon(
            imageVector = Icons.Default.HeartBroken,
            contentDescription = null,
            tint = if (islike) Color.Red else Color.Gray,
            modifier = Modifier.size(48.dp)
                .clickable {islike = !islike}.padding(start = 24.dp)



        )
        Spacer(modifier = Modifier.width(10.dp))
        Text(text = "2.4k")
        Spacer(modifier = Modifier.width(20.dp))
        Icon(
            imageVector = Icons.Default.Chat,
            contentDescription = null,
            tint = Color.Gray,
            modifier = Modifier.size(48.dp).padding(horizontal = 10.dp),

            )
        Text(text = "128")
    }
}