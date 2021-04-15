package com.example.newsappt1.presentation.scene.searchnews

import android.net.Uri
import android.webkit.URLUtil
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.newsappt1.*
import com.example.newsappt1.data.model.News
import com.example.newsappt1.data.model.NewsList
import com.example.newsappt1.data.remote.RetrofitInitializer
import com.example.newsappt1.presentation.common.Event
import com.example.newsappt1.presentation.common.ScreenState
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import kotlin.collections.ArrayList
import kotlin.concurrent.schedule

class SearchNewsViewModel: ViewModel() {

    private val service = RetrofitInitializer.getNewsApiService()

    private var timer = Timer()

    private val _screenState: MutableLiveData<ScreenState<List<News>>> = MutableLiveData()
    val screenState: LiveData<ScreenState<List<News>>>
        get() = _screenState

    private val _navigationShowEntireNews: MutableLiveData<Event<Uri>> = MutableLiveData()
    val navigationShowEntireNews: LiveData<Event<Uri>>
        get() = _navigationShowEntireNews

    private val _message: MutableLiveData<Event<Int>> = MutableLiveData()
    val message: LiveData<Event<Int>>
        get() = _message

    fun onSearchEditTextChanged(text: CharSequence?) {
        timer.cancel()
        timer = Timer()
        timer.schedule(2000) {
            searchNews(text.toString())
        }
    }

    fun onNewsItemClicked(clickedNews: News) {
        if (URLUtil.isValidUrl(clickedNews.newsUrl)) {
            val webpage: Uri = Uri.parse(clickedNews.newsUrl)
            _navigationShowEntireNews.value = Event(webpage)
        } else {
            _message.value = Event(R.string.invalid_url)
        }
    }

    fun onShowNewsResolveActivityFail() {
        _message.value = Event(R.string.browser_needed)
    }

    private fun searchNews(searchText: String) {
        _screenState.postValue(ScreenState.Loading())

        service.getEverything(searchText).enqueue(object : Callback<NewsList> {
            override fun onResponse(call: Call<NewsList>, response: Response<NewsList>) {
                if (response.isSuccessful && response.body() != null && response.body()!!.items.isNotEmpty()) {
                    _screenState.postValue(ScreenState.Success(response.body()!!.items as ArrayList<News>))
                } else {
                    _screenState.postValue(ScreenState.Error())
                }
            }

            override fun onFailure(call: Call<NewsList>, t: Throwable) {
                _screenState.postValue(ScreenState.Error())
            }

        })
    }

}