package com.example.newspulse.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newspulse.data.NewsItem
import com.example.newspulse.repository.NewsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {

    private val _trendingNews = MutableStateFlow<List<NewsItem>>(emptyList())
    val trendingNews: StateFlow<List<NewsItem>> = _trendingNews.asStateFlow()

    private val _breakingNews = MutableStateFlow<List<NewsItem>>(emptyList())
    val breakingNews: StateFlow<List<NewsItem>> = _breakingNews.asStateFlow()

    private val _latestNews = MutableStateFlow<List<NewsItem>>(emptyList())
    val latestNews: StateFlow<List<NewsItem>> = _latestNews.asStateFlow()

    private val _allNews = MutableStateFlow<List<NewsItem>>(emptyList())
    val allNews: StateFlow<List<NewsItem>> = _allNews.asStateFlow()

    init {
        fetchAllNews()
    }

    fun fetchAllNews() {
        fetchTrending()
        fetchBreaking()
        fetchLatest()
    }

    private fun fetchTrending() = viewModelScope.launch {
        try {
            val response = NewsRepository.fetchTrendingNews()
            _trendingNews.value = response?.articles ?: emptyList()
            updateAllNews()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun fetchBreaking() = viewModelScope.launch {
        try {
            val response = NewsRepository.fetchBreakingNews()
            _breakingNews.value = response?.articles ?: emptyList()
            updateAllNews()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun fetchLatest() = viewModelScope.launch {
        try {
            val response = NewsRepository.fetchLatestNews()
            _latestNews.value = response?.articles ?: emptyList()
            updateAllNews()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun updateAllNews() {
        _allNews.value = _trendingNews.value + _breakingNews.value + _latestNews.value
    }

}

