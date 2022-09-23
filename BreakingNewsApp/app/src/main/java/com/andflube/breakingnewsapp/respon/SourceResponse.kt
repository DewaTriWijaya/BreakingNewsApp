package com.andflube.breakingnewsapp.respon

import com.google.gson.annotations.SerializedName

data class SourceResponse(
    @field:SerializedName("name")
    val name: String,

    @field:SerializedName("id")
    val id: Any
)
