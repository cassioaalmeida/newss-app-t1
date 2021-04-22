package com.example.newsappt1.presentation.scene.newsdetail

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.newsappt1.R
import com.example.newsappt1.common.NewsAppApplication
import com.example.newsappt1.data.model.News
import com.example.newsappt1.databinding.ActivityNewsDetailBinding
import com.example.newsappt1.presentation.common.ScreenState
import javax.inject.Inject

class NewsDetailActivity : AppCompatActivity() {

    companion object {
        const val NEWS_DETAIL_KEY = "NEWS_DETAIL_KEY"
    }

    private lateinit var binding: ActivityNewsDetailBinding

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    lateinit var viewModel: NewsDetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityNewsDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this, viewModelFactory).get(NewsDetailViewModel::class.java)

        val receivedNewsId = intent.getIntExtra(NEWS_DETAIL_KEY, -1)
        viewModel.onIdReceived(receivedNewsId)

//        (application as NewsAppApplication).applicationDI
//            .inject(this, receivedNewsId)

        viewModel.screenState.observe(this) { screenState ->
            when (screenState) {
                is ScreenState.Success -> {
                    displayData(screenState.data)
                    binding.emptyStateIndicator.visibility = View.GONE
                    binding.progressIndicator.visibility = View.GONE
                    binding.successState.visibility = View.VISIBLE
                }
                is ScreenState.Error -> {
                    binding.progressIndicator.visibility = View.GONE
                    binding.successState.visibility = View.GONE
                    binding.emptyStateIndicator.visibility = View.VISIBLE
                }
                is ScreenState.Loading -> {
                    binding.emptyStateIndicator.visibility = View.GONE
                    binding.successState.visibility = View.GONE
                    binding.progressIndicator.visibility = View.VISIBLE
                }
            }
        }

        viewModel.navigationShowNews.observe(this) { uriEvent ->
            uriEvent.handleEvent { uri ->
                val intent = Intent(Intent.ACTION_VIEW, uri)
                if (intent.resolveActivity(packageManager) != null) {
                    startActivity(intent)
                } else {
                    viewModel.onShowNewsResolveActivityFail()
                }
            }
        }

        viewModel.message.observe(this) { messageEvent ->
            messageEvent.handleEvent { message ->
                Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun displayData(news: News) {
        binding.newsTitle.text = news.title

        if (news.description != null) binding.newsDescription.text =
            news.description
        else binding.newsDescription.visibility = View.GONE

        if (news.content != null) binding.newsContent.text = news.content
        else binding.newsContent.visibility = View.GONE

        binding.newsSource.text = getString(
            R.string.news_source,
            news.author ?: getString(R.string.unknown),
            news.source.name
        )

        binding.newsLastUpdate.text =
            getString(R.string.news_lastupdate, news.lastUpdate)

        Glide
            .with(this)
            .load(news.imageUrl)
            .placeholder(R.drawable.ic_no_image)
            .into(binding.newsImage)

        binding.btnShowNews.setOnClickListener {
            viewModel.onShowNewsClicked(news)
        }
    }
}