package com.example.newspulse.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.newspulse.navigation.Route
import com.example.newspulse.ui.components.MyFeaturedPage
import com.example.newspulse.ui.components.MyLatestNewsPage
import com.example.newspulse.ui.components.MyTrendingPage
import com.example.newspulse.ui.viewmodel.HomeViewModel


@Composable
fun homeScreenUI(
    navController: NavController,
    HomeViewModel: HomeViewModel = viewModel()
) {

    val allNews by HomeViewModel.newsItem.collectAsState()


    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(top = 10.dp),
    ) {
        // TODAY TRENDING SECTION
        item {
            MyTrendingPage(
                featuredStories = allNews,
                onNewsClick = { news ->
                    navController.navigate(
                        Route.MyDetailedArticleScreen(
                            image = news.image,
                            title = news.title,
                            description = news.description,
                            content = news.content
                        )
                    )

                })
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
            MyFeaturedPage(featuredStories = allNews,
                onNewsClick = { news ->
                    navController.navigate(
                        Route.MyDetailedArticleScreen(
                            image = news.image,
                            title = news.title,
                            description = news.description,
                            content = news.content
                        )
                    )
        }
            )
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
        items(allNews.size) { index ->
            val currentNews = allNews[index]
            MyLatestNewsPage(
                newsItem = currentNews,
                onNewsClick = { news ->
                    navController.navigate(
                        Route.MyDetailedArticleScreen(
                            image = news.image,
                            title = news.title,
                            description = news.description,
                            content = news.content
                        )

                    )

                }
            )
        }
    }
}
