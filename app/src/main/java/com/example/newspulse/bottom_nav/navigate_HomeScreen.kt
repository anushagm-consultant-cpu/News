package com.example.newspulse.bottom_nav

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.newspulse.R

data class NewsItem(
    val title: String,
    val description: String,
    val image: Int
)

val featuredStories = listOf(
    NewsItem(
        title = "Market Update",
        description = "Global markets react to new digital currency regulations",
        image = R.drawable.img_1
    ),
    NewsItem(
        title = "Tech News",
        description = "New AI models are changing the way we write code forever.",
        image = R.drawable.img_2
    ),
    NewsItem(
        title = "Sports",
        description = "Local team wins championship in a thrilling overtime finish.",
        image = R.drawable.img_3
    )

)

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun homeScreenUI() {

    val scrollState = rememberScrollState()


    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(scrollState)
            .padding(top = 55.dp, start = 20.dp, end = 20.dp, bottom = 30.dp)

    ) {
        Text(
            text = "TODAY TRENDING",

            fontSize = 20.sp,
            fontWeight = FontWeight.SemiBold
        )

        Spacer(modifier = Modifier.height(10.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
                .shadow(
                    elevation = 10.dp,
                    shape = RoundedCornerShape(10.dp)
                )

                .background(
                    color = Color.White,

                    ),
            contentAlignment = Alignment.Center,


            ) {
            Text(
                text = "image 1"
            )

        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Breaking News", fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold
            )
            TextButton(onClick = {}) {
                Text(
                    text = "See all", fontSize = 15.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }



        Text(
            text = "Featured Stories",
            fontSize = 20.sp,
            fontWeight = FontWeight.SemiBold
        )
        Spacer(modifier = Modifier.height(10.dp))

        LazyRow(

            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            items(featuredStories.size) { index ->
                val news = featuredStories[index]
                Box(
                    modifier = Modifier
                        .height(300.dp)
                        .width(270.dp)
                        .shadow(
                            elevation = 10.dp,
                            shape = RoundedCornerShape(25.dp)
                        )
                        .background(
                            color = Color.White,

                            )
                        .clip(RoundedCornerShape(25.dp))

                ) {
                    Column {
                        Image(
                            painter = painterResource(news.image),
                            contentDescription = "image",
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(200.dp),

                            contentScale = ContentScale.Crop
                        )


                        Column(
                            modifier = Modifier
                                .padding(horizontal = 16.dp, vertical = 12.dp)
                        ) {
                            Text(
                                text = news.title,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.Black
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = news.description,
                                fontSize = 14.sp,
                                color = Color.Gray,
                                maxLines = 2,
                                lineHeight = 18.sp
                            )
                        }

                    }

                }
            }
        }


    }
}




