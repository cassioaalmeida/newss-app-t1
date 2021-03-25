package com.example.newsappt1

import com.google.gson.annotations.SerializedName

data class NewsList(
    @SerializedName("articles")
    val items: List<News>
)