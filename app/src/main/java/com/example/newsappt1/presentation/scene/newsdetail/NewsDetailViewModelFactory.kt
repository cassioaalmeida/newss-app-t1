package com.example.newsappt1.presentation.scene.newsdetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.newsappt1.data.model.News
import java.lang.IllegalArgumentException

class NewsDetailViewModelFactory(val news: News) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(NewsDetailViewModel::class.java)){
            return NewsDetailViewModel(news) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}