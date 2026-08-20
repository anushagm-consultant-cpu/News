package com.example.newspulse.ui.components.detailArticle

import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Box
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
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.newspulse.data.NewsItem
import com.example.newspulse.ui.screens.MyDetailedArticleScreen
import com.example.newspulse.ui.viewmodel.HistoryViewModel
import com.example.newspulse.ui.viewmodel.ListenViewModel
import com.example.newspulse.ui.viewmodel.SavedViewModel


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ArticleSwipeScreen(
    article: List<NewsItem>,
    initialpage: Int = 0,
    onBack: () -> Unit,
    savedViewModel: SavedViewModel = hiltViewModel(),
    historyViewModel: HistoryViewModel = hiltViewModel(),
    listenViewModel: ListenViewModel = hiltViewModel()
) {
    val context = LocalContext.current

    var currentArticleList by remember { mutableStateOf(article) }
    var showMuteSheet by remember { mutableStateOf(false) }
    var showAiSummarySheet by remember { mutableStateOf(false) }
    var bounceTrigger by remember { mutableStateOf(0) }
    var reactionEmoji by remember { mutableStateOf<String?>(null) }

    if (currentArticleList.isEmpty() && article.isNotEmpty()) {
        currentArticleList = article
    }

    if (currentArticleList.isNotEmpty()) {
        val safeInitialPage = initialpage.coerceIn(0, currentArticleList.size - 1)
        val pagerState = rememberPagerState(
            initialPage = safeInitialPage,
            pageCount = { currentArticleList.size }
        )

        val savedNews by savedViewModel.savedNews.collectAsState()
        val currentArticle = currentArticleList.getOrNull(pagerState.currentPage)
        val isSaved = savedNews.any { it.title == currentArticle?.title }

        val isPlaying by listenViewModel.isPlaying.collectAsState()
        val progress by listenViewModel.progress.collectAsState()
        val speed by listenViewModel.currentSpeed.collectAsState()

        // Sync both History AND Audio with the current page
        LaunchedEffect(pagerState.currentPage) {
            currentArticle?.let {
                historyViewModel.addToHistory(it)
                listenViewModel.setCurrentArticleAudio(it)
            }
        }

        Scaffold(
            topBar = {
                ArticleDetailTopBar(
                    isSaved = isSaved,
                    bounceTrigger = bounceTrigger,
                    topic = currentArticle?.source?.name ?: "this topic",
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
                            savedViewModel.toggleSave(item)
                            bounceTrigger++
                        }
                    },
                    onMuteClick = {
                        showMuteSheet = true
                    }
                )
            }
        ) { paddingValues ->
            Box(modifier = Modifier.fillMaxSize().padding(paddingValues)) {
                HorizontalPager(
                    state = pagerState,
                    modifier = Modifier.fillMaxSize()
                ) { page ->
                    val newsItem = currentArticleList[page]
                    val isSavedItem = savedNews.any { it.title == newsItem.title }
                    MyDetailedArticleScreen(
                        article = newsItem,
                        isSaved = isSavedItem,
                        onBack = onBack,
                        onDoubleTap = {
                            savedViewModel.toggleSave(newsItem)
                            bounceTrigger++
                        },
                        listenViewModel = listenViewModel,
                        externalEmoji = if (pagerState.currentPage == page) reactionEmoji else null,
                        onEmojiConsumed = { reactionEmoji = null }
                    )
                }

                // Fixed Floating Action Menu with Audio Controls connected
                FloatingActionMenu(
                    modifier = Modifier.align(Alignment.BottomEnd),
                    onAiSummaryClick = { showAiSummarySheet = true },
                    onListenClick = { listenViewModel.togglePlayPause() },
                    onReactClick = { emoji -> reactionEmoji = emoji },
                    onStopClick = { listenViewModel.stopAudio() },
                    isPlaying = isPlaying,
                    onTogglePlay = { listenViewModel.togglePlayPause() },
                    currentProgress = progress,
                    onProgressChange = {  },
                    speed = "${speed}x",
                    onSpeedChange = { listenViewModel.cycleSpeed() }
                )
            }

            if (showMuteSheet && currentArticle != null) {
                MuteSliderBottomSheet(
                    topic = currentArticle.source?.name ?: "this topic",
                    onDismiss = { showMuteSheet = false },
                    onConfirm = { _, _ ->
                        showMuteSheet = false
                    }
                )
            }

            if (showAiSummarySheet) {
                val summeryText =(currentArticle?.description?:"")+ "\n" + (currentArticle?.content?:"")
                AiSummaryBottomSheet(
                    articleText = summeryText,
                    onDismiss = { showAiSummarySheet = false }
                )
            }
        }
    }
}
