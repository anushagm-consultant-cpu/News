package com.example.newspulse.ui.components.profileoptionsScreens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.newspulse.ui.components.homeComponents.MyLatestNewsPage
import com.example.newspulse.ui.viewmodel.HistoryViewModel
import com.example.newspulse.data.NewsItem

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MyreadingHistroy(
    onBackClick: () -> Unit,
    onNewsClick: (NewsItem) -> Unit,
    viewModel: HistoryViewModel = viewModel()
) {
    val historyNews by viewModel.historyNews.collectAsState()

    Scaffold(
        topBar = {
            SettingTopAppBar(
                title = "Reading History",
                onBackClick = onBackClick
            )
        }
    ) { innerPadding ->
        if (historyNews.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "No reading history found")
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                items(historyNews) { article ->
                    MyLatestNewsPage(
                        newsItem = article,
                        onNewsClick = { onNewsClick(article) }
                    )
                }
            }
        }
    }
}
