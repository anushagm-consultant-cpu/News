package com.example.newspulse.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

data class NewsResponse(
    val status: String,
    val totalResults: Int,
    val articles: List<NewsItem>
)

data class Source(
    val id: String?,
    val name: String
)

@Entity(tableName = "news_articles")
data class NewsItem(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0, // Room will auto-increment this
    val source: Source?,
    val author: String?,
    val title: String,
    val description: String?,
    val url: String?, // Added URL field
    @SerializedName("urlToImage")
    val image: String?,
    val content: String?,
    val publishedAt: String,
    val isSaved: Boolean = false,
    val isHistory: Boolean = false,
    val savedAt: Long = 0,
    val historyAt: Long = 0
)
