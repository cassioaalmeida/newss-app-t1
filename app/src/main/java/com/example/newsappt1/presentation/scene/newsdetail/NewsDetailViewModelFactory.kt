package com.example.newsappt1.presentation.scene.newsdetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class NewsDetailViewModelFactory(val newsId: Int) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(NewsDetailViewModel::class.java)){
            return NewsDetailViewModel(newsId) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}