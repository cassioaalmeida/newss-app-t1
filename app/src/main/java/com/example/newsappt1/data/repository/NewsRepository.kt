package com.example.newsappt1.data.repository

import com.example.newsappt1.data.cache.NewsCDS
import com.example.newsappt1.data.model.News
import com.example.newsappt1.data.model.NewsList
import com.example.newsappt1.data.remote.RetrofitInitializer
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewsRepository {

    private val newsRDS = RetrofitInitializer.getNewsApiService()
    private val newsCDS = NewsCDS()

    fun getNewsList(
        onSuccess: (List<News>) -> Unit,
        onError: () -> Unit
    ) {
        newsRDS.getTopHeadlines("br").enqueue(object : Callback<NewsList> {
            override fun onResponse(call: Call<NewsList>, response: Response<NewsList>) {
                if (response.isSuccessful && response.body() != null && response.body()!!.items.isNotEmpty()) {
                    val remoteNews: List<News> = response.body()!!.items
                    // alguma lógica para inserir ids nos itens
                    newsCDS.saveNewsList(remoteNews)
                    onSuccess(remoteNews)
                } else {
                    val cacheNews: List<News>? = newsCDS.getNewsList()
                    if (cacheNews != null) {
                        onSuccess(cacheNews)
                    } else {
                        onError()
                    }
                }
            }

            override fun onFailure(call: Call<NewsList>, t: Throwable) {
                val cacheNews: List<News>? = newsCDS.getNewsList()
                if (cacheNews != null) {
                    onSuccess(cacheNews)
                } else {
                    onError()
                }
            }
        })
    }

    fun searchNews(
        searchText: String,
        onSuccess: (List<News>) -> Unit,
        onError: () -> Unit
    ) {
        newsRDS.getEverything(searchText).enqueue(object : Callback<NewsList> {
            override fun onResponse(call: Call<NewsList>, response: Response<NewsList>) {
                if (response.isSuccessful && response.body() != null && response.body()!!.items.isNotEmpty()) {
                    onSuccess(response.body()!!.items)
                } else {
                    onError()
                }
            }

            override fun onFailure(call: Call<NewsList>, t: Throwable) {
                onError()
            }

        })
    }

}