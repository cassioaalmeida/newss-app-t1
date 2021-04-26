package com.example.newsappt1.data.cache

import android.util.Log
import com.example.newsappt1.data.model.News
import io.paperdb.Paper
import javax.inject.Inject

class NewsCDS @Inject constructor() {

    private val cacheStrings: List<String> = listOf("cache1", "cache2", "cache3")

    init {
        Log.i("Inject list", cacheStrings.toString())
    }

    companion object {
        private const val NEWS_LIST_KEY = "NEWS_LIST_KEY"
    }

    fun saveNewsList(newsList: List<News>) {
        Paper.book().write(NEWS_LIST_KEY, newsList)
    }

    fun getNewsList(
        onSuccess: (List<News>) -> Unit,
        onError: () -> Unit
    ) {
        val newsList: List<News>? = Paper.book().read(NEWS_LIST_KEY)
        if (newsList != null)
            onSuccess(newsList)
        else
            onError()
    }

    fun getNews(newsId: Int, onSuccess: (News) -> Unit, onError: () -> Unit) {
        val news: News? = Paper.book().read<List<News>?>(NEWS_LIST_KEY)?.find { news ->
            news.id == newsId
        }

        if (news != null) {
            onSuccess(news)
        } else {
            onError()
        }
    }
}