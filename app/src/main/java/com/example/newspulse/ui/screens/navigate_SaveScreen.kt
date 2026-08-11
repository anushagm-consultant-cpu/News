package com.example.newspulse.ui.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.example.newspulse.navigation.Route
import com.example.newspulse.ui.components.homeComponents.MyLatestNewsPage
import com.example.newspulse.ui.viewmodel.SavedViewModel


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun SaveScreen(
    navController: NavController,
    viewModel: SavedViewModel,
    innerPadding: PaddingValues
) {

    val savedNews by viewModel.savedNews.collectAsState()

    if (savedNews.isEmpty()) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentAlignment = Alignment.Center
        ) {
            Text(text = "No saved articles")
        }
    } else {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            items(savedNews) { article ->
                MyLatestNewsPage(
                    newsItem = article,
                    onNewsClick = { news ->
                        navController.navigate(
                            Route.MyDetailedArticleScreen(
                                image = news.image,
                                title = news.title,
                                description = news.description,
                                content = news.content,
                                fromSaved = true // Passing the flag here
                            )
                        )
                    }
                )
            }
        }
    }
}
