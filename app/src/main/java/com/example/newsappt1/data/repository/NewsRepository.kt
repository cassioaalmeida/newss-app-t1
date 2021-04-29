package com.example.newsappt1.data.repository

import com.example.newsappt1.data.cache.NewsCDS
import com.example.newsappt1.data.model.News
import com.example.newsappt1.data.remote.NewsRDS
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.security.InvalidParameterException
import javax.inject.Inject

class NewsRepository @Inject constructor(
    private val newsRDS: NewsRDS,
    private val newsCDS: NewsCDS,
) {

    fun getNewsList(): Single<List<News>> =
        newsRDS.getTopHeadlines("br")
            .map {
                if (it.items.isEmpty()) throw InvalidParameterException("News items can't be empty")
                else it
            }
            .map { it.items }
            .map { it.mapIndexed { index, news -> news.copy(id = index) } }
            .flatMap { newsCDS.saveNewsList(it).toSingleDefault(it) }
            .onErrorResumeNext { newsCDS.getNewsList() }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    fun searchNews(searchText: String): Single<List<News>> =
        newsRDS.getEverything(searchText)
            .map {
                if (it.items.isEmpty()) throw InvalidParameterException("News items can't be empty")
                else it
            }
            .map { it.items }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

    fun getNews(newsId: Int): Single<News> = newsCDS.getNews(newsId)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())

}