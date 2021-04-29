package com.example.newsappt1.data.repository

import com.example.newsappt1.data.cache.NewsCDS
import com.example.newsappt1.data.model.News
import com.example.newsappt1.data.model.NewsList
import com.example.newsappt1.data.remote.NewsRDS
import io.reactivex.Single
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.security.InvalidParameterException
import javax.inject.Inject

class NewsRepository @Inject constructor(
    private val newsRDS: NewsRDS,
    private val newsCDS: NewsCDS,
) {

//    fun getNewsList(
//        onSuccess: (List<News>) -> Unit,
//        onError: () -> Unit
//    ) {
//        newsRDS.getTopHeadlines("br").enqueue(object : Callback<NewsList> {
//            override fun onResponse(call: Call<NewsList>, response: Response<NewsList>) {
//                if (response.isSuccessful && response.body() != null && response.body()!!.items.isNotEmpty()) {
//                    val remoteNews: List<News> = response.body()!!.items
//                    val newsWithIds = remoteNews.mapIndexed { index, news ->
//                        news.copy(id = index)
//                    }
//                    newsCDS.saveNewsList(newsWithIds)
//                    onSuccess(newsWithIds)
//                } else {
//                    newsCDS.getNewsList(onSuccess, onError)
//                }
//            }
//
//            override fun onFailure(call: Call<NewsList>, t: Throwable) {
//                newsCDS.getNewsList(onSuccess, onError)
//            }
//        })
//    }

    fun getNewsList(): Single<List<News>> =
        newsRDS.getTopHeadlines("br")
            .map {  objectNewsList ->
                if (objectNewsList.items.isEmpty()) throw InvalidParameterException("News items can't be empty")
                else objectNewsList
            }
            .map { objectNewsList ->
                objectNewsList.items
            }
            .map { listOfNews ->
                listOfNews.mapIndexed { index, news ->
                    news.copy(id = index)
                }
            }.flatMap { listOfNewsWithIds ->
                newsCDS.saveNewsList(listOfNewsWithIds)
                    .andThen(Single.just(listOfNewsWithIds))
            }
            .onErrorResumeNext {
                newsCDS.getNewsList()
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