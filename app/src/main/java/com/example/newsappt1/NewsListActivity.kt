package com.example.newsappt1

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsappt1.databinding.ActivityNewsListBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewsListActivity : AppCompatActivity() {

    private lateinit var viewModel: NewsListViewModel
    lateinit var binding: ActivityNewsListBinding

    private lateinit var adapter: NewsListAdapter
    private val service = RetrofitInitializer.getNewsApiService()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityNewsListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Log.i("ViewModel LifeCycle", "Obteve instancia do ViewModel")
        viewModel = ViewModelProvider(this).get(NewsListViewModel::class.java)

        binding.btnTryAgain.setOnClickListener {
            getDataFromService()
        }

        adapter = NewsListAdapter(this)
        binding.recyclerviewNews.adapter = adapter
        binding.recyclerviewNews.layoutManager = LinearLayoutManager(this)

        if (viewModel.newsList == null) {
            getDataFromService()
        } else {
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
                    viewModel.newsList = response.body()!!.items as ArrayList<News>
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
        viewModel.newsList?.let {
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

}