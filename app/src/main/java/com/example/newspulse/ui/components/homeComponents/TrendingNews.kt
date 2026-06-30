package com.example.newspulse.ui.components.homeComponents

import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.newspulse.data.NewsItem
import com.example.newspulse.ui.components.profileoptionsScreens.AutoText


@Composable
fun MyTrendingPage(
    featuredStories: List<NewsItem>,
    onNewsClick: (NewsItem) -> Unit = {}
) {
    Column(modifier = Modifier.padding(horizontal = 20.dp)) {
        Spacer(modifier = Modifier.height(6.dp))
        AutoText(
            text = "TRENDING NEWS",
            baseFontSize = 16.sp,
            fontWeight = FontWeight.ExtraBold,
            color = MaterialTheme.colorScheme.onBackground,

        )
        Spacer(modifier = Modifier.height(10.dp))

        val pagerState = rememberPagerState(pageCount = { featuredStories.size })

        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxWidth()
                .height(230.dp)
                .shadow(elevation = 8.dp, shape = RoundedCornerShape(20.dp))
                .clip(RoundedCornerShape(20.dp))
                .background(color = MaterialTheme.colorScheme.surface)
        ) { page ->
            val news = featuredStories[page]

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .clickable { onNewsClick(news) }
            ) {
                AsyncImage(
                    model = news.image,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize(),
                    placeholder = androidx.compose.ui.res.painterResource(id = com.example.newspulse.R.drawable.img_12)
                )

                // Added a gradient background for much better text readability
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(Color.Transparent, Color.Black.copy(alpha = 0.8f)),
                                startY = 300f
                            )
                        )
                )

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 20.dp),
                    verticalArrangement = Arrangement.Bottom
                ) {
                    AutoText(
                        text = news.title ?: "No Title Avaiable",
                        color = Color.White,
                        baseFontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        style = TextStyle(
                            shadow = Shadow(
                                color = Color.Black.copy(0.5f),
                                offset = Offset(2f, 2f),
                                blurRadius = 4f
                            )
                        )
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        AutoText(
                            text = "By ${news.author ?: "Unknown Author"}",
                            color = Color.White.copy(alpha = 0.9f),
                            baseFontSize = 12.sp,
                            fontWeight = FontWeight.SemiBold
                        )




                    }
                    Spacer(modifier = Modifier.height(12.dp))
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        //Page Indicator
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            repeat(featuredStories.size) { index ->
                val isSelected = pagerState.currentPage == index
                Box(
                    modifier = Modifier
                        .padding(4.dp)
                        .height(6.dp)
                        .width(if (isSelected) 20.dp else 8.dp)
                        .clip(CircleShape)
                        .background(
                            if (isSelected) MaterialTheme.colorScheme.primary  else Color.LightGray
                        )
                )
            }
        }
        Spacer(modifier = Modifier.height(12.dp))
    }
}



