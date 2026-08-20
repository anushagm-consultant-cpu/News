package com.example.newspulse.ui.Intent

import com.example.newspulse.data.NewsItem

data class ExploreState(
    val availableTopics: List<String> = listOf(
        "Technology", "Sports", "Politics", "Nature", "Health",
        "Business", "Entertainment", "Science", "Travel", "World", "Education", "Lifestyle"
    ),
    val selectedTopics: Set<String> = setOf("Technology", "Sports", "Politics", "Nature", "Health", "Business","Entertainment", "Science"),
    val availableLengths: List<String> = listOf("Short", "Medium", "Long"),
    val preferredLength: String = "Medium",
    val searchResults: List<NewsItem> = emptyList(),
    val isSearching: Boolean = false,
    val error: String? = null
)

sealed class ExploreIntent {
    data class SearchNews(val query: String) : ExploreIntent()
    object ClearSearch : ExploreIntent()
    data class AddTopic(val topic: String) : ExploreIntent()
    data class ToggleTopicSelection(val topic: String) : ExploreIntent()
    data class SetPreferredLength(val length: String) : ExploreIntent()
}
