package com.example.newsappt1

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsappt1.databinding.ActivityNewsListBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewsListActivity : AppCompatActivity() {

    companion object {
        const val NEWS_KEY = "NEWS_KEY"
    }

    lateinit var binding: ActivityNewsListBinding
    private var newsList: ArrayList<News>? = null
    private lateinit var adapter: NewsListAdapter
    private val service = RetrofitInitializer.getNewsApiService()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityNewsListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnTryAgain.setOnClickListener {
            getDataFromService()
        }

        adapter = NewsListAdapter(this)
        binding.recyclerviewNews.adapter = adapter
        binding.recyclerviewNews.layoutManager = LinearLayoutManager(this)

        if (savedInstanceState?.getParcelableArrayList<News>(NEWS_KEY) == null) {
            getDataFromService()
        } else {
            newsList = savedInstanceState.getParcelableArrayList<News>(NEWS_KEY)
            showList()
        }

    }

    fun getDataFromService() {
        binding.recyclerviewNews.visibility = View.GONE
        binding.emptyStateIndicator.visibility = View.GONE
        binding.progressIndicator.visibility = View.VISIBLE

        service.getTopHeadlines("br").enqueue(object : Callback<NewsList> {
            override fun onResponse(call: Call<NewsList>, response: Response<NewsList>) {
                // verifica se o retorno foi feito com sucesso
                if (response.isSuccessful && response.body() != null) {
                    // tenho acesso a minha lista de notícias
                    newsList = response.body()!!.items as ArrayList<News>
                    showList()
                } else {
                    showEmptyState()
                }

            }

            override fun onFailure(call: Call<NewsList>, t: Throwable) {
                showEmptyState()
            }

        })
    }

    fun showEmptyState() {
        binding.progressIndicator.visibility = View.GONE
        binding.recyclerviewNews.visibility = View.GONE
        binding.emptyStateIndicator.visibility = View.VISIBLE
    }

    fun showList() {
        newsList?.let {
            adapter.addData(it) { news ->
                val navigateToDetailsIntent = Intent(this, NewsDetailActivity::class.java)
                navigateToDetailsIntent.putExtra(NewsDetailActivity.NEWS_DETAIL_KEY, news)
                startActivity(navigateToDetailsIntent)
            }

            binding.progressIndicator.visibility = View.GONE
            binding.emptyStateIndicator.visibility = View.GONE
            binding.recyclerviewNews.visibility = View.VISIBLE
        }
    }


    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelableArrayList(NEWS_KEY, newsList)
    }


}