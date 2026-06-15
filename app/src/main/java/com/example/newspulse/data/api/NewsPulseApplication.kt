package com.example.newspulse.data.api

import android.app.Application
import coil.ImageLoader
import coil.ImageLoaderFactory
import com.example.newspulse.data.ArticleDatabase
import com.example.newspulse.repository.NewsRepository

class NewsPulseApplication : Application(), ImageLoaderFactory {
    override fun onCreate() {
        super.onCreate()

        val database = ArticleDatabase.getDatabase(this)
        NewsRepository.init(database.getArticleDao())
    }

    override fun newImageLoader(): ImageLoader {
        return ImageLoader.Builder(this)
            .okHttpClient {
                RetrofitClient.okHttpClient
            }
            .build()
    }
}
