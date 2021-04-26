package com.example.newsappt1.data.repository

import android.util.Log
import com.example.newsappt1.data.cache.NewsCDS
import com.example.newsappt1.data.model.News
import com.example.newsappt1.data.model.NewsList
import com.example.newsappt1.data.remote.NewsRDS
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class NewsRepository @Inject constructor(
    private val newsRDS: NewsRDS,
    private val newsCDS: NewsCDS,
) {
    private val repositoryStrings: List<String> = listOf("repository1", "repository2", "repository3")

    init {
        Log.i("Inject list", repositoryStrings.toString())
    }

    fun getNewsList(
        onSuccess: (List<News>) -> Unit,
        onError: () -> Unit
    ) {
        newsRDS.getTopHeadlines("br").enqueue(object : Callback<NewsList> {
            override fun onResponse(call: Call<NewsList>, response: Response<NewsList>) {
                if (response.isSuccessful && response.body() != null && response.body()!!.items.isNotEmpty()) {
                    val remoteNews: List<News> = response.body()!!.items
                    val newsWithIds = remoteNews.mapIndexed { index, news ->
                        news.copy(id = index)
                    }
                    newsCDS.saveNewsList(newsWithIds)
                    onSuccess(newsWithIds)
                } else {
                    newsCDS.getNewsList(onSuccess, onError)
                }
            }

            override fun onFailure(call: Call<NewsList>, t: Throwable) {
                newsCDS.getNewsList(onSuccess, onError)
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

    fun getNews(newsId: Int, onSuccess: (News) -> Unit, onError: () -> Unit) {
        newsCDS.getNews(newsId, onSuccess, onError)
    }

}