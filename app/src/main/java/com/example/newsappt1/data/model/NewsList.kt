package com.example.newsappt1.data.model

import com.example.newsappt1.data.model.News
import com.google.gson.annotations.SerializedName

data class NewsList(
    @SerializedName("articles")
    val items: List<News>
)