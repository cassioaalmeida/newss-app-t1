package com.example.newsappt1.presentation.scene.newslist

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsappt1.common.NewsAppApplication
import com.example.newsappt1.data.model.News
import com.example.newsappt1.presentation.common.NewsListAdapter
import com.example.newsappt1.presentation.common.ScreenState
import com.example.newsappt1.databinding.ActivityNewsListBinding
import com.example.newsappt1.presentation.scene.newsdetail.NewsDetailActivity
import com.example.newsappt1.presentation.scene.searchnews.SearchNewsActivity
import javax.inject.Inject

class NewsListActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var viewModel: NewsListViewModel
    lateinit var binding: ActivityNewsListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityNewsListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        (application as NewsAppApplication).applicationComponent.inject(this)

        Log.i("ViewModel LifeCycle", "Obteve instancia do ViewModel")
        viewModel = ViewModelProvider(this, viewModelFactory).get(NewsListViewModel::class.java)

        binding.btnTryAgain.setOnClickListener {
            viewModel.onTryAgainClicked()
        }

        binding.fabSearchNews.setOnClickListener {
            viewModel.onSearchNewsClicked()
        }

        val adapter = NewsListAdapter(this)
        binding.recyclerviewNews.adapter = adapter
        binding.recyclerviewNews.layoutManager = LinearLayoutManager(this)

        viewModel.navigationDetail.observe(this) { newsIdEvent ->
            Log.i("LiveDataEvent", "Recebi navigation detail")
            newsIdEvent.handleEvent { newsId ->
                val navigateToDetailsIntent = Intent(this, NewsDetailActivity::class.java)
                navigateToDetailsIntent.putExtra(NewsDetailActivity.NEWS_DETAIL_KEY, newsId)
                startActivity(navigateToDetailsIntent)
            }
        }

        viewModel.navigationSearchNews.observe(this) { event ->
            event.handleEvent {
                val navigateToSearchNews = Intent(this, SearchNewsActivity::class.java)
                startActivity(navigateToSearchNews)
            }
        }

        viewModel.screenState.observe(this) { lastScreenState ->
            Log.i("LiveDataEvent", "Recebi screen state")
            when (lastScreenState) {
                is ScreenState.Success<List<News>> -> {
                    adapter.setData(lastScreenState.data) { news ->
                        viewModel.onNewsItemClicked(news)
                    }
                    binding.progressIndicator.visibility = View.GONE
                    binding.emptyStateIndicator.visibility = View.GONE
                    binding.recyclerviewNews.visibility = View.VISIBLE
                }
                is ScreenState.Error -> {
                    binding.progressIndicator.visibility = View.GONE
                    binding.recyclerviewNews.visibility = View.GONE
                    binding.emptyStateIndicator.visibility = View.VISIBLE
                }
                is ScreenState.Loading -> {
                    binding.recyclerviewNews.visibility = View.GONE
                    binding.emptyStateIndicator.visibility = View.GONE
                    binding.progressIndicator.visibility = View.VISIBLE
                }
                else -> throw IllegalStateException("Unknown ScreenState")
            }
        }
    }
}