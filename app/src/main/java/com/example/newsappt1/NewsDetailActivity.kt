package com.example.newsappt1

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.webkit.URLUtil
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.newsappt1.databinding.ActivityNewsDetailBinding

class NewsDetailActivity : AppCompatActivity() {

    companion object {
        const val NEWS_DETAIL_KEY = "NEWS_DETAIL_KEY"
    }

    private lateinit var binding: ActivityNewsDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityNewsDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val news = intent.getParcelableExtra<News>(NEWS_DETAIL_KEY)

        if (news != null) {
            binding.newsTitle.text = news.title

            if (news.description != null) binding.newsDescription.text = news.description
            else binding.newsDescription.visibility = View.GONE

            if(news.content != null) binding.newsContent.text = news.content
            else binding.newsContent.visibility = View.GONE

            binding.newsSource.text = getString(R.string.news_source, news.author ?: getString(R.string.unknown), news.source.name)

            binding.newsLastUpdate.text = getString(R.string.news_lastupdate, news.lastUpdate)

            Glide
                .with(this)
                .load(news.imageUrl)
                .placeholder(R.drawable.ic_no_image)
                .into(binding.newsImage)

            binding.btnShowNews.setOnClickListener {
                if (URLUtil.isValidUrl(news.newsUrl)) {
                    val webpage: Uri = Uri.parse(news.newsUrl)
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
        } else {
            Toast.makeText(this, "news can't be null", Toast.LENGTH_SHORT).show()
            finish()
        }
    }

}