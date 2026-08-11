package com.example.newspulse.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [NewsItem::class], version = 5)
@TypeConverters(Converters::class)
abstract class ArticleDatabase : RoomDatabase() {
    abstract fun getArticleDao(): ArticleDao

    companion object {

        private var instance: ArticleDatabase? = null


        @Synchronized
        fun getDatabase(context: Context): ArticleDatabase {
            return instance ?: synchronized(this) {
                val newInstance = Room.databaseBuilder(
                    context.applicationContext,
                    ArticleDatabase::class.java,
                    "article_database"
                )
                    .fallbackToDestructiveMigration(false)
                .build()
                instance = newInstance
                newInstance
            }
        }
    }
}
