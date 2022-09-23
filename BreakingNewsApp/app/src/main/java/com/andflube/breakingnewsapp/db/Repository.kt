package com.andflube.breakingnewsapp.db

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.map
import com.andflube.breakingnewsapp.BuildConfig
import com.andflube.breakingnewsapp.db.data.NewsDB
import com.andflube.breakingnewsapp.network.NewsApi

class Repository private constructor(
    private val apiService: NewsApi,
    private val dao: Dao
) {
    fun getHeadlineNews(): LiveData<Result<List<NewsDB>>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.getNews(BuildConfig.API_KEY)
            val articles = response.articles
            val newsList = articles.map { article ->
                val isBookmarked = dao.isNewsBookmarked(article.title)
                NewsDB(
                    article.title,
                    article.publishedAt,
                    article.description,
                    article.author,
                    article.urlToImage,
                    article.url,
                    isBookmarked
                )
            }
            dao.deleteAll()
            dao.insertNews(newsList)
        } catch (e: Exception) {
            Log.d("NewsRepository", "getHeadlineNews: ${e.message.toString()} ")
            emit(Result.Error(e.message.toString()))
        }
        val localData: LiveData<Result<List<NewsDB>>> = dao.getNews().map { Result.Success(it) }
        emitSource(localData)
    }

    fun getBookmarkedNews(): LiveData<List<NewsDB>> {
        return dao.getBookmarkedNews()
    }

    suspend fun setNewsBookmark(news: NewsDB, bookmarkState: Boolean) {
        news.isBookmarked = bookmarkState
        dao.updateNews(news)
    }

    companion object {
        @Volatile
        private var instance: Repository? = null
        fun getInstance(
            apiService: NewsApi,
            newsDao: Dao
        ): Repository =
            instance ?: synchronized(this) {
                instance ?: Repository(apiService, newsDao)
            }.also { instance = it }

    }
}