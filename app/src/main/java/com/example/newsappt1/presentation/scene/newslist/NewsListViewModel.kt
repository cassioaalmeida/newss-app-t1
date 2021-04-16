package com.example.newsappt1.presentation.scene.newslist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.newsappt1.data.model.News
import com.example.newsappt1.data.repository.NewsRepository
import com.example.newsappt1.presentation.common.Event
import com.example.newsappt1.presentation.common.ScreenState

class NewsListViewModel() : ViewModel() {

    private val _screenState: MutableLiveData<ScreenState<List<News>>> = MutableLiveData()
    val screenState: LiveData<ScreenState<List<News>>>
        get() = _screenState

    private val _navigationDetail: MutableLiveData<Event<News>> = MutableLiveData()
    val navigationDetail: LiveData<Event<News>>
        get() = _navigationDetail

    private val _navigationSearchNews: MutableLiveData<Event<Unit>> = MutableLiveData()
    val navigationSearchNews: LiveData<Event<Unit>>
        get() = _navigationSearchNews

    private val repository = NewsRepository()

    init {
        getNewsList()
    }

    fun onTryAgainClicked() {
        getNewsList()
    }

    fun onSearchNewsClicked() {
        _navigationSearchNews.value = Event(Unit)
    }

    fun onNewsItemClicked(news: News) {
        _navigationDetail.value = Event(news)
    }

    private fun getNewsList() {
        _screenState.value = ScreenState.Loading()

        repository.getNewsList(
            onSuccess = { newsList ->
                _screenState.value = ScreenState.Success(newsList)
            },
            onError = {
                _screenState.value = ScreenState.Error()
            }
        )
    }
}