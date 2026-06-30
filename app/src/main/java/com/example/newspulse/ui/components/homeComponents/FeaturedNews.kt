package com.example.newspulse.ui.components.homeComponents

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.newspulse.data.NewsItem
import com.example.newspulse.ui.components.profileoptionsScreens.AutoText


@Composable
fun MyFeaturedPage(featuredStories: List<NewsItem>,
                   onNewsClick: (NewsItem) -> Unit = {}){

    Column {


        LazyRow(
            contentPadding = PaddingValues(horizontal = 20.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(featuredStories.size) { index ->
                val newsItem = featuredStories[index]

                Box(
                    modifier = Modifier
                        .width(270.dp)

                        .shadow(elevation = 5.dp, shape = RoundedCornerShape(25.dp))
                        .clip(RoundedCornerShape(25.dp))
                        .background(MaterialTheme.colorScheme.surface)
                        .clickable { onNewsClick(newsItem) }
                ) {
                    Column {
                        AsyncImage(
                            model = newsItem.image,
                            contentDescription = null,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(200.dp),
                            contentScale = ContentScale.Crop,
                            placeholder =  androidx.compose.ui.res.painterResource(id = com.example.newspulse.R.drawable.img_12)
                        )
                        Column(modifier = Modifier.padding(16.dp)) {
                            AutoText(
                                text = newsItem.title,
                                baseFontSize = 16.sp,
                                color= MaterialTheme.colorScheme.onSurface,
                                fontWeight = FontWeight.Bold,
                                maxLines = 2,

                                )
                            Spacer(modifier = Modifier.height(4.dp))

                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                AutoText(
                                    text = "By",
                                    color = MaterialTheme.colorScheme.primary,
                                    baseFontSize = 11.sp,
                                    fontWeight = FontWeight.Bold
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                AutoText(
                                    text = newsItem.author ?: "Unknown Author",
                                    color = MaterialTheme.colorScheme.primary,
                                    baseFontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    maxLines = 1
                                )
                            }
                        }
                    }
                }
            }
        }
    }
    Spacer(modifier = Modifier.height(18.dp))

}