package com.andflube.breakingnewsapp.di

import android.content.Context
import com.andflube.breakingnewsapp.db.NewsDatabase
import com.andflube.breakingnewsapp.db.Repository
import com.andflube.breakingnewsapp.network.NewsRetrofit

object Injection {
    fun provideRepository(context: Context): Repository {
        val newsApi = NewsRetrofit.getApiService()
        val database = NewsDatabase.getInstance(context)
        val dao = database.dao()
        return Repository.getInstance(newsApi, dao)
    }
}