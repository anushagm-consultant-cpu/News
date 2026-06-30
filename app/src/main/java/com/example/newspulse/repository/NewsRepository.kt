package com.example.newspulse.repository

import com.example.newspulse.data.ArticleDao
import com.example.newspulse.data.NewsItem
import com.example.newspulse.data.NewsResponse
import com.example.newspulse.data.api.NewsApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NewsRepository @Inject constructor(
    private val apiService: NewsApiService,
    private val articleDao: ArticleDao
) {
    suspend fun fetchTrendingNews(): NewsResponse? {
        return try {
            val response = apiService.getTrendingNews(
                query ="apple",
                sortBy = "popularity",
                apiKey = "eefee1a52611470e91fffc447798d5f0"
            )
            if (response.isSuccessful) response.body() else null
        } catch (e: Exception) {
            null
        }
    }

    suspend fun fetchBreakingNews(): NewsResponse? {
        return try {
            val response = apiService.getBreakingNews(
                country = "us",
                apiKey = "eefee1a52611470e91fffc447798d5f0"
            )
            if (response.isSuccessful){
                response.body()
            }else{
                null
            }
        } catch (e: Exception) {
            null
        }
    }

    suspend fun fetchLatestNews(): NewsResponse? {
        return try {
            val response = apiService.getLatestNews(
                query = "technology",
                apiKey = "eefee1a52611470e91fffc447798d5f0"
            )
            if (response.isSuccessful){
                response.body()
            } else{
                null
            }
        } catch (e: Exception) {
            null
        }
    }



    val savedNews: Flow<List<NewsItem>>
        get() = articleDao.getSavedArticles()?: flowOf(emptyList())

    val readingHistory: Flow<List<NewsItem>>
        get() = articleDao.getHistoryArticles() ?: flowOf(emptyList())
    suspend fun toggleSave(newsItem: NewsItem) {
        articleDao.let { dao ->
            val existingItem = dao.getArticleByTitle(newsItem.title)

            if (existingItem?.isSaved == true) {
                // If it's already saved, delete it from the database
                dao.deleteArticle(existingItem)
            } else {
                // If it's not saved, add it (or update the existing history item to be 'saved')
                val itemToSave = (existingItem ?: newsItem).copy(
                    isSaved = true,
                    savedAt = System.currentTimeMillis()//to maintain the order of the saved articles
                )
                dao.insertArticle(itemToSave)
            }
        }
    }

    suspend fun addToHistory(newsItem: NewsItem) {
        articleDao.let { dao ->
            val existingItem = dao.getArticleByTitle(newsItem.title)

            // If it exists, we update it; if not, we create it.
            // In both cases, we set isHistory = true and update the time.
            val itemToHistory = (existingItem ?: newsItem).copy(
                isHistory = true,
                historyAt = System.currentTimeMillis()
            )
            dao.insertArticle(itemToHistory)
        }
    }

}