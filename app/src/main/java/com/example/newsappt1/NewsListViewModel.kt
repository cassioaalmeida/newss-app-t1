package com.example.newsappt1

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewsListViewModel() : ViewModel() {

    private val _screenState: MutableLiveData<ScreenState<List<News>>> = MutableLiveData()
    val screenState: LiveData<ScreenState<List<News>>>
        get() = _screenState

    private val _navigationDetail: MutableLiveData<Event<News>> = MutableLiveData()
    val navigationDetail: LiveData<Event<News>>
        get() = _navigationDetail

    private val service = RetrofitInitializer.getNewsApiService()

    init {
        getDataFromService()
    }

    fun onTryAgainClicked() {
        getDataFromService()
    }

    fun onNewsItemClicked(news: News) {
        _navigationDetail.value = Event(news)
    }

    private fun getDataFromService() {
        _screenState.value = ScreenState.Loading()

        service.getTopHeadlines("br").enqueue(object : Callback<NewsList> {
            override fun onResponse(call: Call<NewsList>, response: Response<NewsList>) {
                // verifica se o retorno foi feito com sucesso
                if (response.isSuccessful && response.body() != null) {
                    // tenho acesso a minha lista de notícias
                    _screenState.value = ScreenState.Success(response.body()!!.items as ArrayList<News>)
                } else {
                    _screenState.value = ScreenState.Error()
                }
            }

            override fun onFailure(call: Call<NewsList>, t: Throwable) {
                _screenState.value = ScreenState.Error()
            }
        })
    }
}