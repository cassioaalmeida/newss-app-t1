package com.example.newsappt1

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class News(
    val title: String,
    val description: String,
    val imageUrl: String,
    val content: String,
    val author: String,
    val source: String,
    val lastUpdate: String,
    val newsUrl: String
) : Parcelable