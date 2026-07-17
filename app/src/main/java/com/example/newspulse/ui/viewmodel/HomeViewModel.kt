package com.example.newspulse.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newspulse.repository.NewsRepository
import com.example.newspulse.ui.Intent.HomeIntent
import com.example.newspulse.ui.Intent.HomeState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: NewsRepository
): ViewModel() {

//    private val _trendingNews = MutableStateFlow<List<NewsItem>>(emptyList())
//    val trendingNews: StateFlow<List<NewsItem>> = _trendingNews.asStateFlow()
//
//    private val _breakingNews = MutableStateFlow<List<NewsItem>>(emptyList())
//    val breakingNews: StateFlow<List<NewsItem>> = _breakingNews.asStateFlow()
//
//    private val _latestNews = MutableStateFlow<List<NewsItem>>(emptyList())
//    val latestNews: StateFlow<List<NewsItem>> = _latestNews.asStateFlow()
//
//    val allNews: StateFlow<List<NewsItem>> = combine(_trendingNews, _breakingNews, _latestNews) { t, b, l ->
//        t + b + l
//    }
//    .flowOn(Dispatchers.Default)
//    .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
//
//    private val _searchResults = MutableStateFlow<List<NewsItem>>(emptyList())
//    val searchResults: StateFlow<List<NewsItem>> = _searchResults.asStateFlow()
//
//    private val _isSearching = MutableStateFlow(false)
//    val isSearching: StateFlow<Boolean> = _isSearching.asStateFlow()
//
//    private val _error = MutableStateFlow<String?>(null)
//    val error: StateFlow<String?> = _error.asStateFlow()


    private val _state = MutableStateFlow(HomeState())
    val state: StateFlow<HomeState> = _state.asStateFlow()

    fun onIntent(intent: HomeIntent) {
        when (intent) {
            is HomeIntent.FetchAllNews -> fetchAllNews()
            is HomeIntent.ClearError -> clearError()
        }
    }

    private fun fetchAllNews() {
        _state.update { it.copy(error = null) }
        fetchTrending()
        fetchBreaking()
        fetchLatest()
    }

    private fun clearError() {
        _state.update { it.copy(error = null) }
    }

    private fun setError() {
        _state.update { currentState ->
            if (currentState.error == null) {
                currentState.copy(error = "Failed to fetch news. Please check your network connection.")
            } else {
                currentState.copy()
            }
        }
    }

    private fun updateAllNews() {
        _state.update { currentState ->
            currentState.copy(
                allNews = currentState.trendingNews + currentState.breakingNews + currentState.latestNews
            )
        }
    }

    private fun fetchTrending() = viewModelScope.launch(Dispatchers.IO) {
        try {
            val response = repository.fetchTrendingNews()
            if (response != null && !response.articles.isNullOrEmpty()) {
                _state.update { it.copy(trendingNews = response.articles) }
                updateAllNews()
            } else {
                setError()
            }
        } catch (e: Exception) {
            setError()
        }
    }

    private fun fetchBreaking() = viewModelScope.launch(Dispatchers.IO) {
        try {
            val response = repository.fetchBreakingNews()
            if (response != null && !response.articles.isNullOrEmpty()) {
                _state.update { it.copy(breakingNews = response.articles) }
                updateAllNews()
            } else {
                setError()
            }
        } catch (e: Exception) {
            setError()
        }
    }

    private fun fetchLatest() = viewModelScope.launch(Dispatchers.IO) {
        try {
            val response = repository.fetchLatestNews()
            if (response != null && !response.articles.isNullOrEmpty()) {
                _state.update { it.copy(latestNews = response.articles) }
                updateAllNews()
            } else {
                setError()
            }
        } catch (e: Exception) {
            setError()
        }
    }
}
