package com.example.newspulse.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.newspulse.R
import com.example.newspulse.data.NewsItem




@Preview(showBackground = true, showSystemUi = true)
@Composable
fun homeScreenUI() {
    // Sample data list
    val featuredStories = listOf(
        NewsItem(
            "Market Update",
            "Global markets react to new digital currency regulations",
            R.drawable.img_1
        ),
        NewsItem(
            "Tech News",
            "New AI models are changing the way we write code forever.",
            R.drawable.img_2
        ),
        NewsItem(
            "Sports",
            "Local team wins championship in a thrilling overtime finish.",
            R.drawable.img_3
        )
    )

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(top = 10.dp),


        ) {
        // TODAY TRENDING SECTION
        item {
            Column(modifier = Modifier.padding(horizontal = 20.dp)) {
                Text(
                    text = "TODAY TRENDING",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(10.dp))

                val pagerState = rememberPagerState(pageCount = { featuredStories.size })

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(250.dp)
                        .shadow(elevation = 8.dp, shape = RoundedCornerShape(20.dp))
                        .clip(RoundedCornerShape(20.dp))
                        .background(color = Color.White),

                    ) {

                        HorizontalPager(
                            state = pagerState,
                            modifier = Modifier
                                .fillMaxSize()


                        ) { page ->
                            val news = featuredStories[page]

                               Box(

                               ) {
                                   Image(
                                       painter = painterResource(news.image),
                                       contentDescription = null,
                                       contentScale = ContentScale.Crop,
                                       modifier = Modifier.fillMaxSize()
                                   )

                                   Box() {
                                     Column(
                                         modifier = Modifier.padding(top = 100.dp, start = 20.dp, end = 20.dp)
                                     ) {
                                         Text(text = news.description,
                                             color = Color.White,

                                             fontSize = 28.sp,
                                             fontWeight = FontWeight.Bold,
                                         style= TextStyle(
                                             shadow = Shadow(
                                                 color = Color.Black.copy(0.8f),
                                                 offset = Offset(5f,5f),
                                                 blurRadius = 10f
                                             )
                                         )
                                         )


                                         Spacer(modifier = Modifier.height(20.dp))
                                         Row() {
                                             Text(text = "4 min ago",
                                                 color = Color.White,
                                                 fontSize = 15.sp,
                                                 fontWeight = FontWeight.SemiBold,
                                                 style= TextStyle(
                                                     shadow = Shadow(
                                                         color = Color.Black.copy(0.8f),
                                                         offset = Offset(5f,5f),
                                                         blurRadius = 10f
                                                     )
                                                 ))

                                             Spacer(modifier = Modifier.width(20.dp))
                                             Text(text = news.title,
                                                 color = Color.White,
                                                 fontSize = 15.sp,
                                                 fontWeight = FontWeight.SemiBold,
                                                 style= TextStyle(
                                                     shadow = Shadow(
                                                         color = Color.Black.copy(0.8f),
                                                         offset = Offset(5f,5f),
                                                         blurRadius = 10f
                                                     )
                                                 ))
                                         }
                                     }
                                   }
                               }




                        }
                    }
                Spacer(modifier = Modifier.height(10.dp))

                //Page Indicator
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                      , horizontalArrangement = Arrangement.Center

                ) {
                    repeat(featuredStories.size) {index->
                        Box(
                            modifier = Modifier.padding(4.dp)
                                .height(6.dp)
                                .clip(CircleShape)
                                .width(
                                    if(pagerState.currentPage==index){
                                        20.dp

                                    } else {
                                        8.dp
                                    }
                                )
                                .background(
                                    if(pagerState.currentPage==index) {
                                        colorResource(id = R.color.teal_700)

                                    }else
                                     Color.LightGray
                                )


                        ) { }

                    }
                }



            }
        }

        //  BREAKING NEWS HEADER
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Breaking News",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
                TextButton(onClick = {}) {
                    Text(
                        text = "See all",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }

        //  FEATURED STORIES (HORIZONTAL ROW)
        item {
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

        // LATEST NEWS HEADER
        item {
            Text(
                text = "Latest News",
                modifier = Modifier.padding(horizontal = 20.dp),
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold
            )
        }

        // LATEST NEWS LIST
        items(6) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical =8.dp)
                    .shadow(2.dp, RoundedCornerShape(12.dp))
                    .background(Color.White, RoundedCornerShape(12.dp))
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
                        painter = painterResource(R.drawable.img_3),
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                }
                Spacer(modifier = Modifier.width(16.dp))

                Column {
                    Text(
                        text = "LOCAL",
                        modifier = Modifier.padding(bottom = 3.dp),
                        fontWeight = FontWeight.Bold,
                        color = colorResource(id=R.color.teal_700),
                        fontSize = 12.sp,

                        )
                    Text(
                        text = "Short description Short descriptionShort descriptionShort descriptionShort descriptionShort descriptionShort descriptionShort descriptionShort descriptionShort description",
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
    }
}