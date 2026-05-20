package com.example.newspulse.ui.viewmodel


import androidx.lifecycle.ViewModel
import com.example.newspulse.data.NewsItem
import com.example.newspulse.data.repository.NewsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class HomeViewModel: ViewModel() {

    private val repository= NewsRepository()
    private val _newsItems = MutableStateFlow<List<NewsItem>>(emptyList())
    val newsItem: StateFlow<List<NewsItem>> = _newsItems.asStateFlow()

    init {
        loadData()
    }

    private fun loadData() {
        _newsItems.value=repository.fectchNews()
    }

}