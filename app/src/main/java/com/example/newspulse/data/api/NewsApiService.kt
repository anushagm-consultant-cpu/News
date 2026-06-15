package com.example.newspulse.data.api

import com.example.newspulse.data.NewsResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApiService {

    //trending
    @GET("v2/everything")
    suspend fun getTrendingNews(
        @Query("q") query: String,
        @Query("from") from: String? = null,
        @Query("to") to: String? = null,
        @Query("sortBy") sortBy: String? = "popularity",
        @Query("apiKey") apiKey: String
    ): Response<NewsResponse>

//braking news
    @GET("v2/top-headlines")
    suspend fun getBreakingNews(
        @Query("country") country: String,
        @Query("apiKey") apiKey: String
    ): Response<NewsResponse>


//latest news
    @GET("v2/everything")
    suspend fun getLatestNews(
        @Query("q") query: String,
        @Query("apiKey") apiKey: String
    ): Response<NewsResponse>

}
