package com.example.newsappt1

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsappt1.databinding.ActivityNewsListBinding
import java.lang.IllegalStateException

class NewsListActivity : AppCompatActivity() {

    private lateinit var viewModel: NewsListViewModel
    lateinit var binding: ActivityNewsListBinding

    private lateinit var adapter: NewsListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityNewsListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Log.i("ViewModel LifeCycle", "Obteve instancia do ViewModel")
        viewModel = ViewModelProvider(this).get(NewsListViewModel::class.java)

        binding.btnTryAgain.setOnClickListener {
            viewModel.onTryAgainClicked()
        }

        adapter = NewsListAdapter(this)
        binding.recyclerviewNews.adapter = adapter
        binding.recyclerviewNews.layoutManager = LinearLayoutManager(this)

        viewModel.newsList.observe(this) { updatedNewsList ->
            adapter.addData(updatedNewsList) { news ->
                val navigateToDetailsIntent = Intent(this, NewsDetailActivity::class.java)
                navigateToDetailsIntent.putExtra(NewsDetailActivity.NEWS_DETAIL_KEY, news)
                startActivity(navigateToDetailsIntent)
            }
        }

        viewModel.screenState.observe(this) { lastScreenState ->
            when (lastScreenState) {
                ScreenState.SUCCESS -> {
                    binding.progressIndicator.visibility = View.GONE
                    binding.emptyStateIndicator.visibility = View.GONE
                    binding.recyclerviewNews.visibility = View.VISIBLE
                }
                ScreenState.ERROR -> {
                    binding.progressIndicator.visibility = View.GONE
                    binding.recyclerviewNews.visibility = View.GONE
                    binding.emptyStateIndicator.visibility = View.VISIBLE
                }
                ScreenState.LOADING -> {
                    binding.recyclerviewNews.visibility = View.GONE
                    binding.emptyStateIndicator.visibility = View.GONE
                    binding.progressIndicator.visibility = View.VISIBLE
                }
                else -> throw IllegalStateException("Unknown ScreenState")
            }
        }
    }
}