package com.example.newspulse.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newspulse.data.ArticleDatabase
import com.example.newspulse.data.NewsItem
import com.example.newspulse.repository.NewsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val repository: NewsRepository
): ViewModel() {

fun addToHistory(article: NewsItem) {
    viewModelScope.launch {
        repository.addToHistory(article)
    }
}
    val historyNews: StateFlow<List<NewsItem>> = repository.readingHistory.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),//delay prevents the database connection from restarting if the user rotates the screen or quickly switches apps.
        initialValue = emptyList()
    )
}
