package com.example.newsappt1

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewsListViewModel(): ViewModel() {

    val newsList: MutableLiveData<List<News>> = MutableLiveData()

    private val service = RetrofitInitializer.getNewsApiService()

    init {
        getDataFromService()
    }

    fun getDataFromService() {
        //view.showLoading()

        service.getTopHeadlines("br").enqueue(object : Callback<NewsList> {
            override fun onResponse(call: Call<NewsList>, response: Response<NewsList>) {
                // verifica se o retorno foi feito com sucesso
                if (response.isSuccessful && response.body() != null) {
                    // tenho acesso a minha lista de notícias
                    newsList.value = response.body()!!.items as ArrayList<News>
                    //view.showList()
                } else {
                    //view.showEmptyState()
                }

            }

            override fun onFailure(call: Call<NewsList>, t: Throwable) {
                //view.showEmptyState()
            }

        })
    }


}