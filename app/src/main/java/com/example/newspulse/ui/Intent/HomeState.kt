package com.example.newspulse.ui.Intent

import com.example.newspulse.data.NewsItem

data class HomeState (
    val trendingNews: List<NewsItem> = emptyList(),
    val breakingNews: List<NewsItem> = emptyList(),
    val latestNews: List<NewsItem> = emptyList(),
    val allNews: List<NewsItem> = emptyList(),
    val error: String? = null
)

sealed class HomeIntent {
    object FetchAllNews : HomeIntent()
    object ClearError : HomeIntent()
}
