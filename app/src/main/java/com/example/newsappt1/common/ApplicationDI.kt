package com.example.newsappt1.common

import androidx.lifecycle.ViewModelProvider
import com.example.newsappt1.data.cache.NewsCDS
import com.example.newsappt1.data.remote.NewsRDS
import com.example.newsappt1.data.remote.RetrofitInitializer
import com.example.newsappt1.data.repository.NewsRepository
import com.example.newsappt1.presentation.scene.newsdetail.NewsDetailActivity
import com.example.newsappt1.presentation.scene.newsdetail.NewsDetailViewModel
import com.example.newsappt1.presentation.scene.newsdetail.NewsDetailViewModelFactory

class ApplicationDI {
    fun inject(newsDetailActivity: NewsDetailActivity, receivedNewsId: Int) {
        newsDetailActivity.viewModel =
            ViewModelProvider(
                newsDetailActivity,
                getNewsDetailViewModelFactory(receivedNewsId)
            ).get(NewsDetailViewModel::class.java)
    }

    private fun getNewsDetailViewModelFactory(receivedNewsId: Int): ViewModelProvider.Factory {
        return NewsDetailViewModelFactory(receivedNewsId, getNewsRepository())
    }

    private fun getNewsRepository(): NewsRepository {
        return NewsRepository(getNewsRDS(), getNewsCDS())
    }

    private fun getNewsCDS(): NewsCDS {
        return NewsCDS()
    }

    private fun getNewsRDS(): NewsRDS {
        return RetrofitInitializer.getNewsApiService()
    }


}