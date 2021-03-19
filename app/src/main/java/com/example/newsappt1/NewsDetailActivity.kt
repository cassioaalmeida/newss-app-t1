package com.example.newsappt1

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.newsappt1.databinding.ActivityNewsDetailBinding

class NewsDetailActivity : AppCompatActivity() {

    companion object {
        const val NEWS_DETAIL_KEY = "NEWS_DETAIL_KEY"
    }

    private lateinit var binding: ActivityNewsDetailBinding
    private lateinit var news: News

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityNewsDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        news = intent.getParcelableExtra<News>(NEWS_DETAIL_KEY)!!

        binding.newsTitle.text = news.title
        binding.newsDescription.text = news.description
        binding.newsContent.text = news.content
        binding.newsSource.text = getString(R.string.news_source, news.author, news.source)
        binding.newsLastUpdate.text = getString(R.string.news_lastupdate, news.lastUpdate)

        Glide
            .with(this)
            .load(news.imageUrl)
            .placeholder(R.drawable.ic_no_image)
            .into(binding.newsImage)

        binding.btnShowNews.setOnClickListener {

        }
    }

}