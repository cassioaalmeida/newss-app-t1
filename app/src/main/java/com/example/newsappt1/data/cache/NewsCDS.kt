package com.example.newsappt1.data.cache

import com.example.newsappt1.data.model.News
import io.paperdb.Paper

class NewsCDS {

    companion object {
        private const val NEWS_LIST_KEY = "NEWS_LIST_KEY"
    }

    fun saveNewsList(newsList: List<News>) {
        Paper.book().write(NEWS_LIST_KEY, newsList)
    }

    fun getNewsList(): List<News>? {
        return Paper.book().read(NEWS_LIST_KEY)
    }
}