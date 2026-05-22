package com.example.newspulse.ui.components.detailArticle

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.newspulse.data.NewsItem
import com.example.newspulse.ui.screens.MyDetailedArticleScreen

@Composable
fun ArticleSwipeScreen(article: List<NewsItem>,Initialpage:Int = 0,onBack:()->Unit){
    val pagerState = rememberPagerState(
        initialPage = Initialpage,
        pageCount = {article.size}
    )
    Scaffold(
        topBar = {
            ArticleDetailTopBar(
                onBack,
                onShareClick = { /* Handle share button click */ },
                onSaveClick = { /* Handle save button click */ }
            )
        }
    ){
        paddingValues ->
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxSize()
                .padding(paddingValues)
        ) {
                page->
            val newsItem = article[page]
            MyDetailedArticleScreen(
                image = newsItem.image,
                title = newsItem.title,
                description = newsItem.description,
                content = newsItem.content,
                onBack = onBack
            )
        }
    }
    }
