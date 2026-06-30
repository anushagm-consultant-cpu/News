package com.example.newspulse.ui.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
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
import com.example.newspulse.navigation.Route
import com.example.newspulse.ui.components.homeComponents.MyFeaturedPage
import com.example.newspulse.ui.components.homeComponents.MyLatestNewsPage
import com.example.newspulse.ui.components.homeComponents.MyTrendingPage
import com.example.newspulse.ui.viewmodel.HomeViewModel
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.example.newspulse.ui.components.profileoptionsScreens.AutoText

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun homeScreenUI(
    navController: NavController,
    innerPadding: PaddingValues,
    homeViewModel: HomeViewModel = viewModel()
) {

    val trending by homeViewModel.trendingNews.collectAsState()
    val breaking by homeViewModel.breakingNews.collectAsState()
    val latest by homeViewModel.latestNews.collectAsState()

    val error by homeViewModel.error.collectAsState()

    if(error != null){
        AlertDialog(
            onDismissRequest = { homeViewModel.clearError() },
            confirmButton = {
                TextButton(
                    onClick = {
                        homeViewModel.fetchAllNews()
                    }
                ) {
                    Text(text = "Retry")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        homeViewModel.clearError()
                    }
                ) {
                    Text(text = "Dismiss")
                }

            },
            title = {
                Text(text = "Error")
            },
            text = {
                Text(text = error ?: "Something went wrong")
            }
        )
    }

    Box(modifier = Modifier
        .fillMaxSize()
        .background(MaterialTheme.colorScheme.background)
        .padding(innerPadding)) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(top = 10.dp),
        ) {
            // TODAY TRENDING SECTION - Taking top 5
            item {
                MyTrendingPage(
                    featuredStories = trending.take(5),
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
                        .padding(horizontal = 20.dp, vertical = 10.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    AutoText(
                        text = "BREAKING NEWS",
                        baseFontSize  = 16.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = MaterialTheme.colorScheme.onBackground,
                        letterSpacing = 1.sp
                    )

                }
            }
            //  FEATURED STORIES (HORIZONTAL ROW) -  top 5
            item {
                MyFeaturedPage(featuredStories = breaking.take(5),
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
                Spacer(modifier = Modifier.height(10.dp))
            }

            // LATEST NEWS HEADER
            item {
                AutoText(
                    text = "LATEST NEWS",
                    modifier = Modifier.padding(horizontal = 20.dp),
                    baseFontSize = 16.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = MaterialTheme.colorScheme.onBackground,
                    letterSpacing = 1.sp
                )

            }
            // LATEST NEWS LIST
            items(latest) { currentNews ->

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
}
