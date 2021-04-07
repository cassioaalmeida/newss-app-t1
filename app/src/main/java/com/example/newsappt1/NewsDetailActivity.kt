package com.example.newsappt1

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.webkit.URLUtil
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.newsappt1.databinding.ActivityNewsDetailBinding

class NewsDetailActivity : AppCompatActivity() {

    companion object {
        const val NEWS_DETAIL_KEY = "NEWS_DETAIL_KEY"
    }

    private lateinit var binding: ActivityNewsDetailBinding
    private lateinit var viewModel: NewsDetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityNewsDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val news = intent.getParcelableExtra<News>(NEWS_DETAIL_KEY)
        val viewModelFactory = NewsDetailViewModelFactory(news!!)
        viewModel = ViewModelProvider(this, viewModelFactory).get(NewsDetailViewModel::class.java)

        binding.newsTitle.text = viewModel.news.title

        if (viewModel.news.description != null) binding.newsDescription.text =
            viewModel.news.description
        else binding.newsDescription.visibility = View.GONE

        if (viewModel.news.content != null) binding.newsContent.text = viewModel.news.content
        else binding.newsContent.visibility = View.GONE

        binding.newsSource.text = getString(
            R.string.news_source,
            viewModel.news.author ?: getString(R.string.unknown),
            viewModel.news.source.name
        )

        binding.newsLastUpdate.text =
            getString(R.string.news_lastupdate, viewModel.news.lastUpdate)

        Glide
            .with(this)
            .load(viewModel.news.imageUrl)
            .placeholder(R.drawable.ic_no_image)
            .into(binding.newsImage)

        binding.btnShowNews.setOnClickListener {
            if (URLUtil.isValidUrl(viewModel.news.newsUrl)) {
                val webpage: Uri = Uri.parse(viewModel.news.newsUrl)
                val intent = Intent(Intent.ACTION_VIEW, webpage)
                if (intent.resolveActivity(packageManager) != null) {
                    startActivity(intent)
                } else {
                    Toast.makeText(this, "You need to install a browser", Toast.LENGTH_SHORT)
                        .show()
                }
            } else {
                Toast.makeText(this, "Invalid URL", Toast.LENGTH_SHORT).show()
            }
        }
    }

}