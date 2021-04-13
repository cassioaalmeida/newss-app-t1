package com.example.newsappt1

import android.net.Uri
import android.webkit.URLUtil
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class NewsDetailViewModel(news: News) : ViewModel() {

    private val _newsDetail: MutableLiveData<News> = MutableLiveData()
    val newsDetail: LiveData<News>
        get() = _newsDetail

    private val _navigationShowNews: MutableLiveData<Event<Uri>> = MutableLiveData()
    val navigationShowNews: LiveData<Event<Uri>>
        get() = _navigationShowNews

    private val _message: MutableLiveData<Event<Int>> = MutableLiveData()
    val message: LiveData<Event<Int>>
        get() = _message

    init {
        _newsDetail.value = news
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