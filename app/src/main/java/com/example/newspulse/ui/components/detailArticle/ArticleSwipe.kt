package com.example.newspulse.ui.components.detailArticle

import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.example.newspulse.data.NewsItem
import com.example.newspulse.repository.NewsRepository
import com.example.newspulse.ui.screens.MyDetailedArticleScreen
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ArticleSwipeScreen(article: List<NewsItem>, initialpage: Int = 0, onBack: () -> Unit,) {
    val context = LocalContext.current
    

    // doesn't cause the pager to jump or remove the article from the current view.
    var currentArticleList by remember { mutableStateOf(article) }
    
    // If the initial list was empty (e.g. still loading), update it once data arrives
    if (currentArticleList.isEmpty() && article.isNotEmpty()) {
        currentArticleList = article
    }

    if (currentArticleList.isNotEmpty()) {
        val safeInitialPage = initialpage.coerceIn(0, currentArticleList.size - 1)
        val pagerState = rememberPagerState(
            initialPage = safeInitialPage,
            pageCount = { currentArticleList.size }
        )
        val scope = rememberCoroutineScope()

        // Observe the live saved status from the repository to update the UI (bookmark icon)
        val savedNews by NewsRepository.savedNews.collectAsState(initial = emptyList())
        val currentArticle = currentArticleList.getOrNull(pagerState.currentPage)
        val isSaved = savedNews.any { it.title == currentArticle?.title }

        // Adding current article to reading history when it's viewed
        LaunchedEffect(pagerState.currentPage) {
            currentArticle?.let {
                scope.launch(Dispatchers.IO) {
                    NewsRepository.addToHistory(it)
                }
            }
        }

        Scaffold(
            topBar = {
                ArticleDetailTopBar(
                    isSaved = isSaved,
                    onBackClick = onBack,
                    onShareClick = {
                        currentArticle?.let { item ->
                            val shareIntent = Intent(Intent.ACTION_SEND).apply {
                                type = "text/plain"
                                putExtra(Intent.EXTRA_SUBJECT, item.title)

                                putExtra(Intent.EXTRA_TEXT, item.url)
                            }
                            context.startActivity(Intent.createChooser(shareIntent, "Share Article"))
                        }
                    },
                    onSaveClick = {
                        currentArticle?.let { item ->
                            scope.launch(Dispatchers.IO) {
                                NewsRepository.toggleSave(item)
                            }
                        }
                    }
                )
            }
        ) { paddingValues ->
            HorizontalPager(
                state = pagerState,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) { page ->
                val newsItem = currentArticleList[page]
                MyDetailedArticleScreen(
                    article = newsItem,
                    onBack = onBack
                )
            }
        }
    }
}
