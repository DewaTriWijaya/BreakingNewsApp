package com.andflube.breakingnewsapp.network

import com.andflube.breakingnewsapp.respon.NewsResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {
    @GET("/v2/top-headlines?country=id")
    suspend fun getNews(@Query("apiKey") apiKey: String): NewsResponse
}