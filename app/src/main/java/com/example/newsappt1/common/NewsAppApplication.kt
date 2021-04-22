package com.example.newsappt1.common

import android.app.Application
import io.paperdb.Paper

class NewsAppApplication: Application() {

    val applicationDI = ApplicationDI()

    override fun onCreate() {
        super.onCreate()

        Paper.init(this)
    }

}