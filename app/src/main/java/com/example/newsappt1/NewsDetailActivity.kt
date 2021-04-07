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

        viewModel = ViewModelProvider(this).get(NewsDetailViewModel::class.java)
        viewModel.news = intent.getParcelableExtra<News>(NEWS_DETAIL_KEY)

        viewModel.news?.let { nonnullNews ->
            binding.newsTitle.text = nonnullNews.title

            if (nonnullNews.description != null) binding.newsDescription.text =
                nonnullNews.description
            else binding.newsDescription.visibility = View.GONE

            if (nonnullNews.content != null) binding.newsContent.text = nonnullNews.content
            else binding.newsContent.visibility = View.GONE

            binding.newsSource.text = getString(
                R.string.news_source,
                nonnullNews.author ?: getString(R.string.unknown),
                nonnullNews.source.name
            )

            binding.newsLastUpdate.text =
                getString(R.string.news_lastupdate, nonnullNews.lastUpdate)

            Glide
                .with(this)
                .load(nonnullNews.imageUrl)
                .placeholder(R.drawable.ic_no_image)
                .into(binding.newsImage)

            binding.btnShowNews.setOnClickListener {
                if (URLUtil.isValidUrl(nonnullNews.newsUrl)) {
                    val webpage: Uri = Uri.parse(nonnullNews.newsUrl)
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
        } ?: throw NullPointerException("News can't be null")
    }

}