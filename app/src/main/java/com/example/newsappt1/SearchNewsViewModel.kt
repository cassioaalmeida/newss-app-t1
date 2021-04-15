package com.example.newsappt1

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchNewsViewModel: ViewModel() {

    private val service = RetrofitInitializer.getNewsApiService()

    private val _searchedNews: MutableLiveData<List<News>> = MutableLiveData()
    val searchedNews: LiveData<List<News>>
        get() = _searchedNews

    fun onSearchEditTextChanged(text: CharSequence?) {
        searchNews(text.toString())
    }

    fun onNewsItemClicked(clickedNews: News) {    }

    private fun searchNews(searchText: String) {
        service.getEverything(searchText).enqueue(object : Callback<NewsList> {
            override fun onResponse(call: Call<NewsList>, response: Response<NewsList>) {
                if (response.isSuccessful && response.body() != null) {
                    _searchedNews.value = response.body()!!.items as ArrayList<News>
                }
            }

            override fun onFailure(call: Call<NewsList>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }
}