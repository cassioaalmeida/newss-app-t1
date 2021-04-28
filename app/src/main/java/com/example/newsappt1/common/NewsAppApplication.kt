package com.example.newsappt1.common

import androidx.multidex.MultiDexApplication
import com.example.newsappt1.common.di.ApplicationComponent
import com.example.newsappt1.common.di.ApplicationModule
import com.example.newsappt1.common.di.DaggerApplicationComponent
import io.paperdb.Paper

class NewsAppApplication : MultiDexApplication() {

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