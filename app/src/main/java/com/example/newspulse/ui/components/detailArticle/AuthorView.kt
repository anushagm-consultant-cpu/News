package com.example.newspulse.ui.components.detailArticle

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.newspulse.data.NewsItem
import com.example.newspulse.data.formatDate
import com.example.newspulse.ui.components.profileoptionsScreens.AutoText


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MyAuthorView(article: NewsItem){
    Column() {
        HorizontalDivider(
            modifier = Modifier.padding(horizontal = 24.dp), 
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f)
        )
        Row(
            modifier = Modifier.fillMaxWidth()
                .padding(horizontal = 30.dp, vertical = 15.dp),
            verticalAlignment = Alignment.CenterVertically

        ) {
            Icon(
                imageVector = Icons.Outlined.Person,
                modifier = Modifier.background(
                    color = MaterialTheme.colorScheme.surfaceVariant,
                    shape = androidx.compose.foundation.shape.CircleShape
                )
                    .size(60.dp)
                    .padding(10.dp),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.secondary,




            )
            Spacer(modifier = Modifier.width(20.dp))

            Column(
                modifier = Modifier.fillMaxWidth().padding(vertical = 6.dp)
            ) {
                AutoText(text = article.author?:"Unknown Author",
                    fontWeight = FontWeight.SemiBold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    color = MaterialTheme.colorScheme.primary,
                    baseFontSize = 18.sp)
                Row (
                    verticalAlignment = Alignment.CenterVertically
                ){
                    Text(
                        text = article.source?.name?:"Unknown",
                        fontSize = 13.sp,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Box(
                        modifier = Modifier
                            .size(4.dp)
                            .clip(CircleShape)
                            .background(Color.Gray.copy(alpha = 0.6f))
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = formatDate(article.publishedAt),
                        fontSize = 13.sp,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                    )
                }
            }

        }
        HorizontalDivider(
            modifier = Modifier.padding(horizontal = 24.dp), 
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.1f)
        )
    }
}
