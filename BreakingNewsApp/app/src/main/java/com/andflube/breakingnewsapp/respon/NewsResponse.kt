package com.andflube.breakingnewsapp.respon

import com.google.gson.annotations.SerializedName

data class NewsResponse(
    @field:SerializedName("totalResults")
    val totalResults: Int,

    @field:SerializedName("articles")
    val articles: List<ArticlesResponse>,

    @field:SerializedName("status")
    val status: String
)
