package com.example.newsappt1.common

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.newsappt1.data.cache.NewsCDS
import com.example.newsappt1.data.remote.NewsRDS
import com.example.newsappt1.data.remote.RetrofitInitializer
import com.example.newsappt1.data.repository.NewsRepository
import com.example.newsappt1.presentation.scene.newsdetail.NewsDetailActivity
import com.example.newsappt1.presentation.scene.newsdetail.NewsDetailViewModel
import com.example.newsappt1.presentation.scene.newsdetail.NewsDetailViewModelFactory
import com.example.newsappt1.presentation.scene.newslist.NewsListActivity
import com.example.newsappt1.presentation.scene.newslist.NewsListViewModel
import com.example.newsappt1.presentation.scene.searchnews.SearchNewsActivity
import com.example.newsappt1.presentation.scene.searchnews.SearchNewsViewModel
import dagger.*
import dagger.multibindings.IntoMap
import javax.inject.Inject
import javax.inject.Provider
import javax.inject.Singleton
import kotlin.reflect.KClass

//class ApplicationDI {
//    fun inject(newsDetailActivity: NewsDetailActivity, receivedNewsId: Int) {
//        newsDetailActivity.viewModel =
//            ViewModelProvider(
//                newsDetailActivity,
//                getNewsDetailViewModelFactory(receivedNewsId)
//            ).get(NewsDetailViewModel::class.java)
//    }
//
//    private fun getNewsDetailViewModelFactory(receivedNewsId: Int): ViewModelProvider.Factory {
//        return NewsDetailViewModelFactory(receivedNewsId, getNewsRepository())
//    }
//
//    private fun getNewsRepository(): NewsRepository {
//        return NewsRepository(getNewsRDS(), getNewsCDS())
//    }
//
//    private fun getNewsCDS(): NewsCDS {
//        return NewsCDS()
//    }
//
//    private fun getNewsRDS(): NewsRDS {
//        return RetrofitInitializer.getNewsApiService()
//    }
//
//
//}

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
    fun newsRDS(): NewsRDS =
        RetrofitInitializer.getNewsApiService()


}


@Singleton
class ViewModelFactory @Inject constructor(private val viewModels: MutableMap<Class<out ViewModel>, Provider<ViewModel>>) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T = viewModels[modelClass]?.get() as T
}

@Target(AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY_GETTER, AnnotationTarget.PROPERTY_SETTER)
@kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
@MapKey
internal annotation class ViewModelKey(val value: KClass<out ViewModel>)

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