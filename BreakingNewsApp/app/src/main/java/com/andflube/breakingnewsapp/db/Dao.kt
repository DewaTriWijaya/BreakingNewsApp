package com.andflube.breakingnewsapp.db

import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.room.Dao
import com.andflube.breakingnewsapp.db.data.NewsDB

@Dao
interface Dao {
    @Query("SELECT * FROM news_information ORDER BY publishedAt DESC")
    fun getNews(): LiveData<List<NewsDB>>

    @Query("SELECT * FROM news_information where bookmarked = 1")
    fun getBookmarkedNews(): LiveData<List<NewsDB>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertNews(news: List<NewsDB>)

    @Update
    suspend fun updateNews(news: NewsDB)

    @Query("DELETE FROM news_information WHERE bookmarked = 0")
    suspend fun deleteAll()

    @Query("SELECT EXISTS(SELECT * FROM news_information WHERE title = :title AND bookmarked = 1)")
    suspend fun isNewsBookmarked(title: String): Boolean
}