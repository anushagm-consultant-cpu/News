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
                    "article_database" //file name
                )
                    .fallbackToDestructiveMigration(false)
                .build()
                instance = newInstance
                newInstance
            }
        }
    }
}




//The code uses two layers of protection to ensure that two different threads don't accidentally create two separate database instances at the exact same time
//@Synchronized: This annotation on the function ensures that only one thread can execute getDatabase at a time.
//synchronized(this): Inside the function, this block acts as a "lock." If instance is still null, the code inside the block runs, creates the database, and assigns it to instance.