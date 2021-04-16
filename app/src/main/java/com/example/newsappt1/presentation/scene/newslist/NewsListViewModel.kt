package com.example.newsappt1.presentation.scene.newslist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.newsappt1.presentation.common.Event
import com.example.newsappt1.data.model.News
import com.example.newsappt1.data.model.NewsList
import com.example.newsappt1.presentation.common.ScreenState
import com.example.newsappt1.data.remote.RetrofitInitializer
import io.paperdb.Paper
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewsListViewModel() : ViewModel() {

    companion object {
        private const val NEWS_LIST_KEY = "NEWS_LIST_KEY"
    }

    private val _screenState: MutableLiveData<ScreenState<List<News>>> = MutableLiveData()
    val screenState: LiveData<ScreenState<List<News>>>
        get() = _screenState

    private val _navigationDetail: MutableLiveData<Event<News>> = MutableLiveData()
    val navigationDetail: LiveData<Event<News>>
        get() = _navigationDetail

    private val _navigationSearchNews: MutableLiveData<Event<Unit>> = MutableLiveData()
    val navigationSearchNews: LiveData<Event<Unit>>
        get() = _navigationSearchNews

    private val service = RetrofitInitializer.getNewsApiService()

    init {
        getDataFromService()
    }

    fun onTryAgainClicked() {
        getDataFromService()
    }

    fun onSearchNewsClicked() {
        _navigationSearchNews.value = Event(Unit)
    }

    fun onNewsItemClicked(news: News) {
        _navigationDetail.value = Event(news)
    }

    private fun getDataFromService() {
        _screenState.value = ScreenState.Loading()

        service.getTopHeadlines("br").enqueue(object : Callback<NewsList> {
            override fun onResponse(call: Call<NewsList>, response: Response<NewsList>) {
                if (response.isSuccessful && response.body() != null && response.body()!!.items.isNotEmpty()) {
                    val remoteNews: List<News> = response.body()!!.items
                    saveInCache(remoteNews)
                    _screenState.value = ScreenState.Success(remoteNews)
                } else {
                    val cacheNews: List<News>? = getDataFromCache()
                    if(cacheNews != null) {
                        _screenState.value = ScreenState.Success(cacheNews)
                    } else {
                        _screenState.value = ScreenState.Error()
                    }
                }
            }

            override fun onFailure(call: Call<NewsList>, t: Throwable) {
                val cacheNews: List<News>? = getDataFromCache()
                if(cacheNews != null) {
                    _screenState.value = ScreenState.Success(cacheNews)
                } else {
                    _screenState.value = ScreenState.Error()
                }
            }
        })
    }

    private fun saveInCache(newsList: List<News>) {
        Paper.book().write(NEWS_LIST_KEY, newsList)
    }

    private fun getDataFromCache(): List<News>? {
        return Paper.book().read(NEWS_LIST_KEY)
    }
}