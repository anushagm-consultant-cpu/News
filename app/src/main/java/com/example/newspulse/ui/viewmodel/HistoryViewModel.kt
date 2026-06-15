package com.example.newspulse.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.newspulse.data.ArticleDatabase
import com.example.newspulse.data.NewsItem
import com.example.newspulse.repository.NewsRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

class HistoryViewModel(application: Application) : AndroidViewModel(application) {

    init {
        val dao = ArticleDatabase.getDatabase(application).getArticleDao()
        NewsRepository.init(dao)
    }

    val historyNews: StateFlow<List<NewsItem>> = NewsRepository.readingHistory.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )
}
