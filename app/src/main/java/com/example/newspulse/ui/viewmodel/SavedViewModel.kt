package com.example.newspulse.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newspulse.data.NewsItem
import com.example.newspulse.repository.NewsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SavedViewModel @Inject constructor(
    private val repository: NewsRepository
) : ViewModel() {

    private val _selectedArticles = MutableStateFlow<Set<Int>>(emptySet())
    val selectedArticles: StateFlow<Set<Int>> = _selectedArticles.asStateFlow()

    private val _isSelectionMode = MutableStateFlow(false)
    val isSelectionMode: StateFlow<Boolean> = _isSelectionMode.asStateFlow()

    fun toggleSave(article: NewsItem) {
        viewModelScope.launch {
            repository.toggleSave(article)
        }
    }

    fun unsaveAll() {
        viewModelScope.launch {
            repository.unsaveAll()
            exitSelectionMode()
        }
    }

    fun deleteSelected() {
        viewModelScope.launch {
            val currentSaved = savedNews.value
            val toDelete = currentSaved.filter { _selectedArticles.value.contains(it.id) }
            toDelete.forEach { repository.toggleSave(it) }
            exitSelectionMode()
        }
    }

    fun toggleSelection(articleId: Int) {
        val current = _selectedArticles.value.toMutableSet()
        if (current.contains(articleId)) {
            current.remove(articleId)
            if (current.isEmpty()) {
                _isSelectionMode.value = false
            }
        } else {
            current.add(articleId)
            _isSelectionMode.value = true
        }
        _selectedArticles.value = current
    }

    fun selectAll() {
        _selectedArticles.value = savedNews.value.map { it.id }.toSet()
        _isSelectionMode.value = true
    }

    fun exitSelectionMode() {
        _selectedArticles.value = emptySet()
        _isSelectionMode.value = false
    }

    val savedNews: StateFlow<List<NewsItem>> = repository.savedNews.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )
}
