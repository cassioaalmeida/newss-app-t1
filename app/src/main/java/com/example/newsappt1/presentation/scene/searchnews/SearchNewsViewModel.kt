package com.example.newsappt1.presentation.scene.searchnews

import android.net.Uri
import android.webkit.URLUtil
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.newsappt1.R
import com.example.newsappt1.data.model.News
import com.example.newsappt1.data.repository.NewsRepository
import com.example.newsappt1.presentation.common.Event
import com.example.newsappt1.presentation.common.ScreenState
import java.util.*
import javax.inject.Inject
import kotlin.concurrent.schedule

class SearchNewsViewModel @Inject constructor(
    private val repository: NewsRepository,
    private val timer: Timer
) : ViewModel() {

    private var timerTask: TimerTask? = null

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
        timerTask?.cancel()

        timerTask = timer.schedule(2000) {
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

        repository.searchNews(
            searchText,
            { searchedNews ->
                _screenState.postValue(ScreenState.Success(searchedNews))
            },
            {
                _screenState.postValue(ScreenState.Error())
            }
        )
    }

}