package com.example.newsappt1.common

import android.app.Application
import io.paperdb.Paper

class NewsAppApplication : Application() {

    lateinit var applicationComponent: ApplicationComponent

    override fun onCreate() {
        super.onCreate()

        Paper.init(this)

        applicationComponent =
            DaggerApplicationComponent.builder()
                .applicationModule(ApplicationModule())
                .build()
    }

}