package com.example.newsappt1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsappt1.databinding.ActivitySearchNewsBinding

class SearchNewsActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySearchNewsBinding
    private lateinit var viewModel: SearchNewsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySearchNewsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(SearchNewsViewModel::class.java)

        val adapter = NewsListAdapter(this)
        binding.searchNewsList.adapter = adapter
        binding.searchNewsList.layoutManager = LinearLayoutManager(this)

        binding.edtSearch.doOnTextChanged { text, _, _, _ ->
            viewModel.onSearchEditTextChanged(text)
        }

        viewModel.screenState.observe(this) { lastScreenState ->
            when(lastScreenState) {
                is ScreenState.Success -> {
                    adapter.setData(lastScreenState.data) { clickedNews ->
                        viewModel.onNewsItemClicked(clickedNews)
                    }
                    binding.progressIndicator.visibility = View.GONE
                    binding.emptyStateIndicator.visibility = View.GONE
                    binding.searchNewsList.visibility = View.VISIBLE
                }
                is ScreenState.Error -> {
                    binding.progressIndicator.visibility = View.GONE
                    binding.searchNewsList.visibility = View.GONE
                    binding.emptyStateIndicator.visibility = View.VISIBLE
                }
                is ScreenState.Loading -> {
                    binding.searchNewsList.visibility = View.GONE
                    binding.emptyStateIndicator.visibility = View.GONE
                    binding.progressIndicator.visibility = View.VISIBLE
                }
            }
        }

    }
}