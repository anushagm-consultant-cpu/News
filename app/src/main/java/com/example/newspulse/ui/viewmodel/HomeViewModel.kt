package com.example.newspulse.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newspulse.data.NewsItem
import com.example.newspulse.repository.NewsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: NewsRepository): ViewModel() {

    private val _trendingNews = MutableStateFlow<List<NewsItem>>(emptyList())
    val trendingNews: StateFlow<List<NewsItem>> = _trendingNews.asStateFlow()

    private val _breakingNews = MutableStateFlow<List<NewsItem>>(emptyList())
    val breakingNews: StateFlow<List<NewsItem>> = _breakingNews.asStateFlow()

    private val _latestNews = MutableStateFlow<List<NewsItem>>(emptyList())
    val latestNews: StateFlow<List<NewsItem>> = _latestNews.asStateFlow()

    private val _allNews = MutableStateFlow<List<NewsItem>>(emptyList())
    val allNews: StateFlow<List<NewsItem>> = _allNews.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    init {
        fetchAllNews()
    }

    fun fetchAllNews() {
        _error.value = null
        fetchTrending()
        fetchBreaking()
        fetchLatest()
    }

    fun clearError() {
        _error.value = null
    }

    private fun setError(){
        if(_error.value == null){
            _error.value = "Failed to fetch news. Please check your network connection."
        }
        }


    private fun fetchTrending() = viewModelScope.launch {
        try {
            val response = repository.fetchTrendingNews()
            if(response!=null  && !response.articles.isNullOrEmpty()) {
                _trendingNews.value = response.articles
                updateAllNews()
            }else{
                setError()
            }
        } catch (e: Exception) {
            setError()
        }
    }

    private fun fetchBreaking() = viewModelScope.launch {
        try {
            val response = repository.fetchBreakingNews()
            if(response!=null && !response.articles.isNullOrEmpty()) {
                _breakingNews.value = response.articles
                updateAllNews()
            }else{
                setError()
            }
        } catch (e: Exception) {
            setError()
        }
    }

    private fun fetchLatest() = viewModelScope.launch {
        try {
            val response = repository.fetchLatestNews()
            if(response!=null  && !response.articles.isNullOrEmpty()) {
                _latestNews.value = response?.articles ?: emptyList()
                updateAllNews()
            }else{
                setError()
            }
        } catch (e: Exception) {
            setError()
        }
    }

    private fun updateAllNews() {
        _allNews.value = _trendingNews.value + _breakingNews.value + _latestNews.value
    }

}
