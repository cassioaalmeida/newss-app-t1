package com.example.newsappt1.presentation.scene.newsdetail

import android.net.Uri
import android.webkit.URLUtil
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.newsappt1.presentation.common.Event
import com.example.newsappt1.data.model.News
import com.example.newsappt1.R
import com.example.newsappt1.data.repository.NewsRepository
import com.example.newsappt1.presentation.common.ScreenState

class NewsDetailViewModel(newsId: Int) : ViewModel() {

    private val repository = NewsRepository()

    private val _screenState: MutableLiveData<ScreenState<News>> = MutableLiveData()
    val screenState: LiveData<ScreenState<News>>
        get() = _screenState

    private val _navigationShowNews: MutableLiveData<Event<Uri>> = MutableLiveData()
    val navigationShowNews: LiveData<Event<Uri>>
        get() = _navigationShowNews

    private val _message: MutableLiveData<Event<Int>> = MutableLiveData()
    val message: LiveData<Event<Int>>
        get() = _message

    init {
        getNewsDetail(newsId)
    }

    private fun getNewsDetail(newsId: Int) {
        if(newsId < 0) throw IllegalArgumentException("newsId must be > 0")

        repository.getNews(
            newsId,
            { news ->
                _screenState.value = ScreenState.Success(news)
            },
            {
                _screenState.value = ScreenState.Error()
            }
        )
    }

    fun onShowNewsClicked(news: News) {
        if (URLUtil.isValidUrl(news.newsUrl)) {
            val webpage: Uri = Uri.parse(news.newsUrl)
            _navigationShowNews.value = Event(webpage)
        } else {
            _message.value = Event(R.string.invalid_url)
        }
    }

    fun onShowNewsResolveActivityFail() {
        _message.value = Event(R.string.browser_needed)
    }
}