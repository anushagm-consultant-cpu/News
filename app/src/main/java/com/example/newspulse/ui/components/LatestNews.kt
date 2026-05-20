package com.example.newspulse.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.newspulse.R
import com.example.newspulse.data.NewsItem


@Composable
fun MyLatestNewsPage(
    newsItem: NewsItem,
    onNewsClick: (NewsItem) -> Unit = {}
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical =8.dp)
            .shadow(2.dp, RoundedCornerShape(12.dp))
            .background(Color.White, RoundedCornerShape(12.dp))
            .clickable{
                onNewsClick(newsItem)
            }
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically,

        ) {
        Box(
            modifier = Modifier
                .size(80.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(Color.LightGray)

        ) {
            Image(
                painter = painterResource(newsItem.image),
                contentDescription = null,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        }
        Spacer(modifier = Modifier.width(16.dp))

        Column {
            Text(
                text = newsItem.title,
                modifier = Modifier.padding(bottom = 3.dp),
                fontWeight = FontWeight.Bold,
                color = colorResource(id=R.color.teal_700),
                fontSize = 12.sp,

                )
            Text(
                text = newsItem.description,
                fontSize = 15.sp,
                color = Color.Black,
                fontWeight = FontWeight.Bold,
                maxLines = 2,
                style = TextStyle(lineHeight = 20.sp)
            )
            Text(
                text = " 2 hours ago",
                modifier = Modifier.padding(top = 3.dp),
                fontSize = 12.sp,
                color = Color.Gray,

                )
        }
    }
}
