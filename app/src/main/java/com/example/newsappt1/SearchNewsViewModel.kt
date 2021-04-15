package com.example.newsappt1

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchNewsViewModel: ViewModel() {

    private val service = RetrofitInitializer.getNewsApiService()

    private val _screenState: MutableLiveData<ScreenState<List<News>>> = MutableLiveData()
    val screenState: LiveData<ScreenState<List<News>>>
        get() = _screenState

    fun onSearchEditTextChanged(text: CharSequence?) {
        searchNews(text.toString())
    }

    fun onNewsItemClicked(clickedNews: News) {    }

    private fun searchNews(searchText: String) {
        _screenState.value = ScreenState.Loading()

        service.getEverything(searchText).enqueue(object : Callback<NewsList> {
            override fun onResponse(call: Call<NewsList>, response: Response<NewsList>) {
                if (response.isSuccessful && response.body() != null) {
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