package com.example.newspulse.ui.components.homeComponents

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.newspulse.data.NewsItem
import com.example.newspulse.data.formatDate
import com.example.newspulse.ui.components.profileoptionsScreens.AutoText


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MyLatestNewsPage(
    newsItem: NewsItem,
    onNewsClick: (NewsItem) -> Unit = {}
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Max)
            .padding(horizontal = 20.dp, vertical =8.dp)
            .shadow(2.dp, RoundedCornerShape(12.dp))
            .background(MaterialTheme.colorScheme.surface, RoundedCornerShape(12.dp))
            .clickable{
                onNewsClick(newsItem)
            }
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically,

        ) {
        Box(
            modifier = Modifier
                .width(80.dp)
                .fillMaxHeight()

                .clip(RoundedCornerShape(8.dp))
                .background(Color.LightGray)

        ) {
            AsyncImage(
                model = newsItem.image,
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop,
                placeholder = androidx.compose.ui.res.painterResource(id = com.example.newspulse.R.drawable.img_12)
            )
        }
        Spacer(modifier = Modifier.width(16.dp))

        Column {
            AutoText(
                text = newsItem.source?.name ?: "Unknown Source",
                modifier = Modifier.padding(bottom = 3.dp),
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary,
                baseFontSize = 12.sp,
                letterSpacing = 1.sp,

            )
            AutoText(
                text = newsItem.title,
                baseFontSize = 15.sp,
                color = MaterialTheme.colorScheme.onSurface,
                fontWeight = FontWeight.Bold,
                maxLines = 2,
                style = TextStyle(lineHeight = 1.3.em),
                overflow = TextOverflow.Ellipsis
            )
            AutoText(
                text = "Published on ${formatDate(newsItem.publishedAt)}",
                modifier = Modifier.padding(top = 3.dp),
                baseFontSize = 12.sp,
                color = Color.Gray,
            )
        }
    }
}
