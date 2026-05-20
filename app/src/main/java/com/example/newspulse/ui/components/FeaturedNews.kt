package com.example.newspulse.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.newspulse.R
import com.example.newspulse.data.NewsItem


@Composable
fun MyFeaturedPage(featuredStories: List<NewsItem>,
                   onNewsClick: (NewsItem) -> Unit = {}){

    Column {
        Text(
            text = "Featured Stories",
            modifier = Modifier.padding(horizontal = 20.dp),
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(10.dp))
        LazyRow(
            contentPadding = PaddingValues(horizontal = 20.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(featuredStories.size) { index ->
                val news = featuredStories[index]

                Box(
                    modifier = Modifier
                        .width(270.dp)
                        .height(270.dp)
                        .shadow(elevation = 5.dp, shape = RoundedCornerShape(25.dp))
                        .clip(RoundedCornerShape(25.dp))
                        .background(Color.White)
                        .clickable { onNewsClick(news) }
                ) {
                    Column {
                        Image(
                            painter = painterResource(news.image),
                            contentDescription = null,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(170.dp),
                            contentScale = ContentScale.Crop
                        )
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(
                                text = news.title.uppercase(),
                                fontSize = 12.sp,
                                color=colorResource(id=R.color.teal_700),
                                fontWeight = FontWeight.Bold,


                                )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = news.description,
                                fontSize = 16.sp,
                                color = Color.Black,
                                fontWeight = FontWeight.Bold,
                                maxLines = 2,
                                style = TextStyle(lineHeight = 20.sp)
                            )
                        }
                    }
                }
            }
        }
    }
    Spacer(modifier = Modifier.height(10.dp))

}