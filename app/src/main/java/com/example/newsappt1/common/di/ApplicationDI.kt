package com.example.newsappt1.common

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.newsappt1.common.di.ViewModelKey
import com.example.newsappt1.data.remote.NewsRDS
import com.example.newsappt1.presentation.common.ViewModelFactory
import com.example.newsappt1.presentation.scene.newsdetail.NewsDetailActivity
import com.example.newsappt1.presentation.scene.newsdetail.NewsDetailViewModel
import com.example.newsappt1.presentation.scene.newslist.NewsListActivity
import com.example.newsappt1.presentation.scene.newslist.NewsListViewModel
import com.example.newsappt1.presentation.scene.searchnews.SearchNewsActivity
import com.example.newsappt1.presentation.scene.searchnews.SearchNewsViewModel
import dagger.*
import dagger.multibindings.IntoMap
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject
import javax.inject.Provider
import javax.inject.Singleton
import kotlin.reflect.KClass

@Singleton
@Component(modules = [ApplicationModule::class, ViewModelModule::class])
interface ApplicationComponent {
    fun inject(newsDetailActivity: NewsDetailActivity)
    fun inject(newsListActivity: NewsListActivity)
    fun inject(searchNewsActivity: SearchNewsActivity)
}

@Module
class ApplicationModule() {

    @Provides
    fun retrofit(): Retrofit = Retrofit.Builder()
        .baseUrl("https://newsapi.org/v2/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Provides
    fun newsRDS(retrofit: Retrofit): NewsRDS =
        retrofit.create(NewsRDS::class.java)

}

@Module
abstract class ViewModelModule {

    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(NewsDetailViewModel::class)
    internal abstract fun newsDetailViewModel(viewModel: NewsDetailViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(NewsListViewModel::class)
    internal abstract fun newsListViewModel(viewModel: NewsListViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SearchNewsViewModel::class)
    internal abstract fun searchNewsViewModel(viewModel: SearchNewsViewModel): ViewModel

    //Add more ViewModels here
}