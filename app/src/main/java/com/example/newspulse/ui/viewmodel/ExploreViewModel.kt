package com.example.newspulse.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newspulse.repository.NewsRepository
import com.example.newspulse.ui.Intent.ExploreIntent
import com.example.newspulse.ui.Intent.ExploreState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExploreViewModel @Inject constructor(
    private val repository: NewsRepository
) : ViewModel() {

    private val _state = MutableStateFlow(ExploreState())
    val state: StateFlow<ExploreState> = _state.asStateFlow()

    fun onIntent(intent: ExploreIntent) {
        when (intent) {
            is ExploreIntent.SearchNews -> searchNews(intent.query)
            is ExploreIntent.ClearSearch -> clearSearch()
            is ExploreIntent.AddTopic -> addTopic(intent.topic)
            is ExploreIntent.ToggleTopicSelection -> toggleTopicSelection(intent.topic)
            is ExploreIntent.SetPreferredLength -> setPreferredLength(intent.length)
        }
    }

    private fun searchNews(query: String) = viewModelScope.launch(Dispatchers.IO) {
        if (query.isBlank()) {
            _state.update {
                it.copy(searchResults = emptyList(),
                    isSearching = false)
            }
            return@launch
        }
        delay(500) //debounce
        _state.update { it.copy(isSearching = true) }

        try {
            val response = repository.searchNews(query)
            if (response != null && !response.articles.isNullOrEmpty()) {
                _state.update {
                    it.copy(
                        searchResults = response.articles,
                        isSearching = false
                    )
                }
            } else {
                _state.update { it.copy(searchResults = emptyList(), isSearching = false) }
            }
        } catch (e: Exception) {
            _state.update { it.copy(error = e.message, isSearching = false) }
        }
    }

    private fun clearSearch() {
        _state.update { it.copy(searchResults = emptyList(), isSearching = false) }
    }

    private fun addTopic(topic: String) {
        _state.update { 
            it.copy(
                availableTopics = it.availableTopics + topic,
                selectedTopics = it.selectedTopics + topic
            )
        }
    }


    private fun toggleTopicSelection(topic: String) {
        _state.update { state ->
            val newSelection = if (state.selectedTopics.contains(topic)) {
                state.selectedTopics - topic
            } else {
                state.selectedTopics + topic
            }
            state.copy(selectedTopics = newSelection)
        }
    }

    private fun setPreferredLength(length: String) {
        _state.update { it.copy(preferredLength = length) }
    }
}
