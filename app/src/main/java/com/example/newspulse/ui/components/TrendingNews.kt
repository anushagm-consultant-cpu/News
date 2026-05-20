package com.example.newspulse.ui.components

import androidx.compose.foundation.Image
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
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
fun MyTrendingPage(
    featuredStories: List<NewsItem>,
    onNewsClick: (NewsItem) -> Unit = {}
) {

    Column(modifier = Modifier.padding(horizontal = 20.dp)) {
        Text(
            text = "TODAY TRENDING",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(10.dp))

        val pagerState = rememberPagerState(pageCount = { featuredStories.size })



        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)
                .shadow(elevation = 8.dp, shape = RoundedCornerShape(20.dp))
                .clip(RoundedCornerShape(20.dp))

                .background(color = Color.White),


            ) { page ->
            val news = featuredStories[page]

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .clickable { onNewsClick(news) }

            ) {
                Image(
                    painter = painterResource(news.image),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )


                Column(
                    modifier = Modifier.padding(top = 100.dp, start = 20.dp, end = 20.dp)
                ) {
                    Text(
                        text = news.description,
                        color = Color.White,

                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold,
                        style = TextStyle(
                            shadow = Shadow(
                                color = Color.Black.copy(0.8f),
                                offset = Offset(5f, 5f),
                                blurRadius = 10f
                            )
                        )
                    )


                    Spacer(modifier = Modifier.height(20.dp))
                    Row {
                        Text(
                            text = "4 min ago",
                            color = Color.White,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.SemiBold,
                            style = TextStyle(
                                shadow = Shadow(
                                    color = Color.Black.copy(0.8f),
                                    offset = Offset(5f, 5f),
                                    blurRadius = 10f
                                )
                            )
                        )

                        Spacer(modifier = Modifier.width(20.dp))
                        Text(
                            text = news.title,
                            color = Color.White,
                            fontSize = 15.sp,
                            fontWeight = FontWeight.SemiBold,
                            style = TextStyle(
                                shadow = Shadow(
                                    color = Color.Black.copy(0.8f),
                                    offset = Offset(5f, 5f),
                                    blurRadius = 10f
                                )
                            )
                        )
                    }
                }
            }
        }






        Spacer(modifier = Modifier.height(10.dp))

        //Page Indicator
        Row(
            modifier = Modifier
                .fillMaxWidth(), horizontalArrangement = Arrangement.Center

        ) {
            repeat(featuredStories.size) { index ->
                Box(
                    modifier = Modifier
                        .padding(4.dp)
                        .height(6.dp)
                        .clip(CircleShape)
                        .width(
                            if (pagerState.currentPage == index) {
                                20.dp

                            } else {
                                8.dp
                            }
                        )
                        .background(
                            if (pagerState.currentPage == index) {
                                colorResource(id = R.color.teal_700)

                            } else
                                Color.LightGray
                        )


                ) { }

            }
        }


    }
}