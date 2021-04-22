package com.example.newsappt1.presentation.scene.newsdetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.newsappt1.data.repository.NewsRepository

class NewsDetailViewModelFactory(
    val newsId: Int,
    private val repository: NewsRepository
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NewsDetailViewModel::class.java)) {
            return NewsDetailViewModel(newsId, repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}