package com.example.newsappt1.presentation.scene.searchnews

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsappt1.presentation.common.NewsListAdapter
import com.example.newsappt1.presentation.common.ScreenState
import com.example.newsappt1.databinding.ActivitySearchNewsBinding
import javax.inject.Inject

class SearchNewsActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var binding: ActivitySearchNewsBinding
    private lateinit var viewModel: SearchNewsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySearchNewsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this, viewModelFactory).get(SearchNewsViewModel::class.java)

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

        viewModel.navigationShowEntireNews.observe(this) { uriEvent ->
            uriEvent.handleEvent { uri ->
                val intent = Intent(Intent.ACTION_VIEW, uri)
                if (intent.resolveActivity(packageManager) != null) {
                    startActivity(intent)
                } else {
                    viewModel.onShowNewsResolveActivityFail()
                }
            }
        }

        viewModel.message.observe(this) { textIdEvent ->
            textIdEvent.handleEvent { textId ->
                Toast.makeText(this, textId, Toast.LENGTH_SHORT).show()
            }
        }

    }
}