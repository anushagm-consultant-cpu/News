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

class SavedViewModel @Inject constructor(
    private val repository: NewsRepository
) : ViewModel() {

fun toggleSave(article: NewsItem) {
    viewModelScope.launch {
        repository.toggleSave(article)
    }
}

    //stateIn ----converts a regular Flow into a StateFlow.
    val savedNews: StateFlow<List<NewsItem>> = repository.savedNews.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()

    )


}


