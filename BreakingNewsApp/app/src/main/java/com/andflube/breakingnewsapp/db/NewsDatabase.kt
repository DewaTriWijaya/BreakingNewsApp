package com.andflube.breakingnewsapp.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.andflube.breakingnewsapp.db.data.NewsDB

@Database(entities = [NewsDB::class], version = 2, exportSchema = false)
abstract class NewsDatabase: RoomDatabase(){
    abstract fun dao(): Dao

    companion object {
        @Volatile
        private var INSTANCE: NewsDatabase? = null

        @Synchronized
        fun getInstance(context: Context): NewsDatabase {
            val instanceA = INSTANCE
            if (instanceA != null) {
                return instanceA
            }
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    NewsDatabase::class.java,
                    "news_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}