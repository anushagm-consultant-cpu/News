package com.example.newspulse.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface  ArticleDao {

    //onConflict = REPLACE: If you try to save an article that already exists (based on its Primary Key), it will overwrite the old one with the new data.
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertArticle(article: NewsItem): Long
    
    @Query("SELECT * FROM news_articles WHERE isSaved = 1 ORDER BY savedAt DESC")
    fun getSavedArticles(): Flow<List<NewsItem>>

    @Query("SELECT * FROM news_articles WHERE isHistory = 1 ORDER BY historyAt DESC")
    fun getHistoryArticles(): Flow<List<NewsItem>>

    @Query("SELECT * FROM news_articles WHERE title = :title LIMIT 1")
    suspend fun getArticleByTitle(title: String): NewsItem?

    @Delete
    suspend fun deleteArticle(article: NewsItem)

    @Query("UPDATE news_articles SET isSaved = 0")
    suspend fun unsaveAllArticles()
}
