package com.example.newsappt1

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsappt1.databinding.ActivityNewsListBinding

class NewsListActivity : AppCompatActivity() {

    companion object {
        const val NEWS_KEY = "NEWS_KEY"
    }

    lateinit var binding: ActivityNewsListBinding
    private lateinit var newsList: ArrayList<News>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityNewsListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        newsList = savedInstanceState?.getParcelableArrayList(NEWS_KEY)
            ?: arrayListOf(
                News(
                    "GS Acquisition Holdings Corp II: Rumors Create An Opportunity",
                    "There are rumors swirling around involving BlockFi and Flipkart. The excitement does create opportunity to position in GSAH that was valued at $15 pre-deal just weeks ago.",
                    "https://static3.seekingalpha.com/assets/og_image_192-59bfd51c9fe6af025b2f9f96c807e46f8e2f06c5ae787b15bf1423e6c676d4db.png",
                    "Yesterday there was a lot of price action in Goldman Sachs Acquisition Company II (NYSE:GSAH). This is a SPAC with a sponsor controlled by an affiliate of Goldman Sachs (NYSE:GS) through its Permanen… [+6435 chars] Yesterday there was a lot of price action in Goldman Sachs Acquisition Company II (NYSE:GSAH). This is a SPAC with a sponsor controlled by an affiliate of Goldman Sachs (NYSE:GS) through its Permanen… [+6435 chars]",
                    "Bram de Haas",
                    "Seeking Alpha",
                    "2021-03-12T11:38:12Z",
                    "https://arstechnica.com/tech-policy/2021/03/i-was-a-teenage-twitter-hacker-graham-ivan-clark-gets-3-year-sentence/"
                ),
                News(
                    "GS Acquisition Holdings Corp II: Rumors Create An Opportunity",
                    "There are rumors swirling around involving BlockFi and Flipkart. The excitement does create opportunity to position in GSAH that was valued at $15 pre-deal just weeks ago.",
                    "https://static3.seekingalpha.com/assets/og_image_192-59bfd51c9fe6af025b2f9f96c807e46f8e2f06c5ae787b15bf1423e6c676d4db.png",
                    "Yesterday there was a lot of price action in Goldman Sachs Acquisition Company II (NYSE:GSAH). This is a SPAC with a sponsor controlled by an affiliate of Goldman Sachs (NYSE:GS) through its Permanen… [+6435 chars] Yesterday there was a lot of price action in Goldman Sachs Acquisition Company II (NYSE:GSAH). This is a SPAC with a sponsor controlled by an affiliate of Goldman Sachs (NYSE:GS) through its Permanen… [+6435 chars]",
                    "Bram de Haas",
                    "Seeking Alpha",
                    "2021-03-12T11:38:12Z",
                    "https://arstechnica.com/tech-policy/2021/03/i-was-a-teenage-twitter-hacker-graham-ivan-clark-gets-3-year-sentence/"
                ),
                News(
                    "GS Acquisition Holdings Corp II: Rumors Create An Opportunity",
                    "There are rumors swirling around involving BlockFi and Flipkart. The excitement does create opportunity to position in GSAH that was valued at $15 pre-deal just weeks ago.",
                    "https://static3.seekingalpha.com/assets/og_image_192-59bfd51c9fe6af025b2f9f96c807e46f8e2f06c5ae787b15bf1423e6c676d4db.png",
                    "Yesterday there was a lot of price action in Goldman Sachs Acquisition Company II (NYSE:GSAH). This is a SPAC with a sponsor controlled by an affiliate of Goldman Sachs (NYSE:GS) through its Permanen… [+6435 chars] Yesterday there was a lot of price action in Goldman Sachs Acquisition Company II (NYSE:GSAH). This is a SPAC with a sponsor controlled by an affiliate of Goldman Sachs (NYSE:GS) through its Permanen… [+6435 chars]",
                    "Bram de Haas",
                    "Seeking Alpha",
                    "2021-03-12T11:38:12Z",
                    "https://arstechnica.com/tech-policy/2021/03/i-was-a-teenage-twitter-hacker-graham-ivan-clark-gets-3-year-sentence/"
                ),
                News(
                    "GS Acquisition Holdings Corp II: Rumors Create An Opportunity",
                    "There are rumors swirling around involving BlockFi and Flipkart. The excitement does create opportunity to position in GSAH that was valued at $15 pre-deal just weeks ago.",
                    "https://static3.seekingalpha.com/assets/og_image_192-59bfd51c9fe6af025b2f9f96c807e46f8e2f06c5ae787b15bf1423e6c676d4db.png",
                    "Yesterday there was a lot of price action in Goldman Sachs Acquisition Company II (NYSE:GSAH). This is a SPAC with a sponsor controlled by an affiliate of Goldman Sachs (NYSE:GS) through its Permanen… [+6435 chars] Yesterday there was a lot of price action in Goldman Sachs Acquisition Company II (NYSE:GSAH). This is a SPAC with a sponsor controlled by an affiliate of Goldman Sachs (NYSE:GS) through its Permanen… [+6435 chars]",
                    "Bram de Haas",
                    "Seeking Alpha",
                    "2021-03-12T11:38:12Z",
                    "https://arstechnica.com/tech-policy/2021/03/i-was-a-teenage-twitter-hacker-graham-ivan-clark-gets-3-year-sentence/"
                ),
                News(
                    "GS Acquisition Holdings Corp II: Rumors Create An Opportunity",
                    "There are rumors swirling around involving BlockFi and Flipkart. The excitement does create opportunity to position in GSAH that was valued at $15 pre-deal just weeks ago.",
                    "https://static3.seekingalpha.com/assets/og_image_192-59bfd51c9fe6af025b2f9f96c807e46f8e2f06c5ae787b15bf1423e6c676d4db.png",
                    "Yesterday there was a lot of price action in Goldman Sachs Acquisition Company II (NYSE:GSAH). This is a SPAC with a sponsor controlled by an affiliate of Goldman Sachs (NYSE:GS) through its Permanen… [+6435 chars] Yesterday there was a lot of price action in Goldman Sachs Acquisition Company II (NYSE:GSAH). This is a SPAC with a sponsor controlled by an affiliate of Goldman Sachs (NYSE:GS) through its Permanen… [+6435 chars]",
                    "Bram de Haas",
                    "Seeking Alpha",
                    "2021-03-12T11:38:12Z",
                    "https://arstechnica.com/tech-policy/2021/03/i-was-a-teenage-twitter-hacker-graham-ivan-clark-gets-3-year-sentence/"
                ),
                News(
                    "GS Acquisition Holdings Corp II: Rumors Create An Opportunity",
                    "There are rumors swirling around involving BlockFi and Flipkart. The excitement does create opportunity to position in GSAH that was valued at $15 pre-deal just weeks ago.",
                    "https://static3.seekingalpha.com/assets/og_image_192-59bfd51c9fe6af025b2f9f96c807e46f8e2f06c5ae787b15bf1423e6c676d4db.png",
                    "Yesterday there was a lot of price action in Goldman Sachs Acquisition Company II (NYSE:GSAH). This is a SPAC with a sponsor controlled by an affiliate of Goldman Sachs (NYSE:GS) through its Permanen… [+6435 chars] Yesterday there was a lot of price action in Goldman Sachs Acquisition Company II (NYSE:GSAH). This is a SPAC with a sponsor controlled by an affiliate of Goldman Sachs (NYSE:GS) through its Permanen… [+6435 chars]",
                    "Bram de Haas",
                    "Seeking Alpha",
                    "2021-03-12T11:38:12Z",
                    "https://arstechnica.com/tech-policy/2021/03/i-was-a-teenage-twitter-hacker-graham-ivan-clark-gets-3-year-sentence/"
                ),
                News(
                    "GS Acquisition Holdings Corp II: Rumors Create An Opportunity",
                    "There are rumors swirling around involving BlockFi and Flipkart. The excitement does create opportunity to position in GSAH that was valued at $15 pre-deal just weeks ago.",
                    "https://static3.seekingalpha.com/assets/og_image_192-59bfd51c9fe6af025b2f9f96c807e46f8e2f06c5ae787b15bf1423e6c676d4db.png",
                    "Yesterday there was a lot of price action in Goldman Sachs Acquisition Company II (NYSE:GSAH). This is a SPAC with a sponsor controlled by an affiliate of Goldman Sachs (NYSE:GS) through its Permanen… [+6435 chars] Yesterday there was a lot of price action in Goldman Sachs Acquisition Company II (NYSE:GSAH). This is a SPAC with a sponsor controlled by an affiliate of Goldman Sachs (NYSE:GS) through its Permanen… [+6435 chars]",
                    "Bram de Haas",
                    "Seeking Alpha",
                    "2021-03-12T11:38:12Z",
                    "https://arstechnica.com/tech-policy/2021/03/i-was-a-teenage-twitter-hacker-graham-ivan-clark-gets-3-year-sentence/"
                ),
                News(
                    "GS Acquisition Holdings Corp II: Rumors Create An Opportunity",
                    "There are rumors swirling around involving BlockFi and Flipkart. The excitement does create opportunity to position in GSAH that was valued at $15 pre-deal just weeks ago.",
                    "https://static3.seekingalpha.com/assets/og_image_192-59bfd51c9fe6af025b2f9f96c807e46f8e2f06c5ae787b15bf1423e6c676d4db.png",
                    "Yesterday there was a lot of price action in Goldman Sachs Acquisition Company II (NYSE:GSAH). This is a SPAC with a sponsor controlled by an affiliate of Goldman Sachs (NYSE:GS) through its Permanen… [+6435 chars] Yesterday there was a lot of price action in Goldman Sachs Acquisition Company II (NYSE:GSAH). This is a SPAC with a sponsor controlled by an affiliate of Goldman Sachs (NYSE:GS) through its Permanen… [+6435 chars]",
                    "Bram de Haas",
                    "Seeking Alpha",
                    "2021-03-12T11:38:12Z",
                    "https://arstechnica.com/tech-policy/2021/03/i-was-a-teenage-twitter-hacker-graham-ivan-clark-gets-3-year-sentence/"
                ),
                News(
                    "GS Acquisition Holdings Corp II: Rumors Create An Opportunity",
                    "There are rumors swirling around involving BlockFi and Flipkart. The excitement does create opportunity to position in GSAH that was valued at $15 pre-deal just weeks ago.",
                    "https://static3.seekingalpha.com/assets/og_image_192-59bfd51c9fe6af025b2f9f96c807e46f8e2f06c5ae787b15bf1423e6c676d4db.png",
                    "Yesterday there was a lot of price action in Goldman Sachs Acquisition Company II (NYSE:GSAH). This is a SPAC with a sponsor controlled by an affiliate of Goldman Sachs (NYSE:GS) through its Permanen… [+6435 chars] Yesterday there was a lot of price action in Goldman Sachs Acquisition Company II (NYSE:GSAH). This is a SPAC with a sponsor controlled by an affiliate of Goldman Sachs (NYSE:GS) through its Permanen… [+6435 chars]",
                    "Bram de Haas",
                    "Seeking Alpha",
                    "2021-03-12T11:38:12Z",
                    "https://arstechnica.com/tech-policy/2021/03/i-was-a-teenage-twitter-hacker-graham-ivan-clark-gets-3-year-sentence/"
                ),
                News(
                    "GS Acquisition Holdings Corp II: Rumors Create An Opportunity",
                    "There are rumors swirling around involving BlockFi and Flipkart. The excitement does create opportunity to position in GSAH that was valued at $15 pre-deal just weeks ago.",
                    "https://static3.seekingalpha.com/assets/og_image_192-59bfd51c9fe6af025b2f9f96c807e46f8e2f06c5ae787b15bf1423e6c676d4db.png",
                    "Yesterday there was a lot of price action in Goldman Sachs Acquisition Company II (NYSE:GSAH). This is a SPAC with a sponsor controlled by an affiliate of Goldman Sachs (NYSE:GS) through its Permanen… [+6435 chars] Yesterday there was a lot of price action in Goldman Sachs Acquisition Company II (NYSE:GSAH). This is a SPAC with a sponsor controlled by an affiliate of Goldman Sachs (NYSE:GS) through its Permanen… [+6435 chars]",
                    "Bram de Haas",
                    "Seeking Alpha",
                    "2021-03-12T11:38:12Z",
                    "https://arstechnica.com/tech-policy/2021/03/i-was-a-teenage-twitter-hacker-graham-ivan-clark-gets-3-year-sentence/"
                ),
                News(
                    "GS Acquisition Holdings Corp II: Rumors Create An Opportunity",
                    "There are rumors swirling around involving BlockFi and Flipkart. The excitement does create opportunity to position in GSAH that was valued at $15 pre-deal just weeks ago.",
                    "https://static3.seekingalpha.com/assets/og_image_192-59bfd51c9fe6af025b2f9f96c807e46f8e2f06c5ae787b15bf1423e6c676d4db.png",
                    "Yesterday there was a lot of price action in Goldman Sachs Acquisition Company II (NYSE:GSAH). This is a SPAC with a sponsor controlled by an affiliate of Goldman Sachs (NYSE:GS) through its Permanen… [+6435 chars] Yesterday there was a lot of price action in Goldman Sachs Acquisition Company II (NYSE:GSAH). This is a SPAC with a sponsor controlled by an affiliate of Goldman Sachs (NYSE:GS) through its Permanen… [+6435 chars]",
                    "Bram de Haas",
                    "Seeking Alpha",
                    "2021-03-12T11:38:12Z",
                    "https://arstechnica.com/tech-policy/2021/03/i-was-a-teenage-twitter-hacker-graham-ivan-clark-gets-3-year-sentence/"
                ),
                News(
                    "GS Acquisition Holdings Corp II: Rumors Create An Opportunity",
                    "There are rumors swirling around involving BlockFi and Flipkart. The excitement does create opportunity to position in GSAH that was valued at $15 pre-deal just weeks ago.",
                    "https://static3.seekingalpha.com/assets/og_image_192-59bfd51c9fe6af025b2f9f96c807e46f8e2f06c5ae787b15bf1423e6c676d4db.png",
                    "Yesterday there was a lot of price action in Goldman Sachs Acquisition Company II (NYSE:GSAH). This is a SPAC with a sponsor controlled by an affiliate of Goldman Sachs (NYSE:GS) through its Permanen… [+6435 chars] Yesterday there was a lot of price action in Goldman Sachs Acquisition Company II (NYSE:GSAH). This is a SPAC with a sponsor controlled by an affiliate of Goldman Sachs (NYSE:GS) through its Permanen… [+6435 chars]",
                    "Bram de Haas",
                    "Seeking Alpha",
                    "2021-03-12T11:38:12Z",
                    "https://arstechnica.com/tech-policy/2021/03/i-was-a-teenage-twitter-hacker-graham-ivan-clark-gets-3-year-sentence/"
                ),
                News(
                    "GS Acquisition Holdings Corp II: Rumors Create An Opportunity",
                    "There are rumors swirling around involving BlockFi and Flipkart. The excitement does create opportunity to position in GSAH that was valued at $15 pre-deal just weeks ago.",
                    "https://static3.seekingalpha.com/assets/og_image_192-59bfd51c9fe6af025b2f9f96c807e46f8e2f06c5ae787b15bf1423e6c676d4db.png",
                    "Yesterday there was a lot of price action in Goldman Sachs Acquisition Company II (NYSE:GSAH). This is a SPAC with a sponsor controlled by an affiliate of Goldman Sachs (NYSE:GS) through its Permanen… [+6435 chars] Yesterday there was a lot of price action in Goldman Sachs Acquisition Company II (NYSE:GSAH). This is a SPAC with a sponsor controlled by an affiliate of Goldman Sachs (NYSE:GS) through its Permanen… [+6435 chars]",
                    "Bram de Haas",
                    "Seeking Alpha",
                    "2021-03-12T11:38:12Z",
                    "https://arstechnica.com/tech-policy/2021/03/i-was-a-teenage-twitter-hacker-graham-ivan-clark-gets-3-year-sentence/"
                ),
                News(
                    "GS Acquisition Holdings Corp II: Rumors Create An Opportunity",
                    "There are rumors swirling around involving BlockFi and Flipkart. The excitement does create opportunity to position in GSAH that was valued at $15 pre-deal just weeks ago.",
                    "https://static3.seekingalpha.com/assets/og_image_192-59bfd51c9fe6af025b2f9f96c807e46f8e2f06c5ae787b15bf1423e6c676d4db.png",
                    "Yesterday there was a lot of price action in Goldman Sachs Acquisition Company II (NYSE:GSAH). This is a SPAC with a sponsor controlled by an affiliate of Goldman Sachs (NYSE:GS) through its Permanen… [+6435 chars] Yesterday there was a lot of price action in Goldman Sachs Acquisition Company II (NYSE:GSAH). This is a SPAC with a sponsor controlled by an affiliate of Goldman Sachs (NYSE:GS) through its Permanen… [+6435 chars]",
                    "Bram de Haas",
                    "Seeking Alpha",
                    "2021-03-12T11:38:12Z",
                    "https://arstechnica.com/tech-policy/2021/03/i-was-a-teenage-twitter-hacker-graham-ivan-clark-gets-3-year-sentence/"
                ),
                News(
                    "GS Acquisition Holdings Corp II: Rumors Create An Opportunity",
                    "There are rumors swirling around involving BlockFi and Flipkart. The excitement does create opportunity to position in GSAH that was valued at $15 pre-deal just weeks ago.",
                    "https://static3.seekingalpha.com/assets/og_image_192-59bfd51c9fe6af025b2f9f96c807e46f8e2f06c5ae787b15bf1423e6c676d4db.png",
                    "Yesterday there was a lot of price action in Goldman Sachs Acquisition Company II (NYSE:GSAH). This is a SPAC with a sponsor controlled by an affiliate of Goldman Sachs (NYSE:GS) through its Permanen… [+6435 chars] Yesterday there was a lot of price action in Goldman Sachs Acquisition Company II (NYSE:GSAH). This is a SPAC with a sponsor controlled by an affiliate of Goldman Sachs (NYSE:GS) through its Permanen… [+6435 chars]",
                    "Bram de Haas",
                    "Seeking Alpha",
                    "2021-03-12T11:38:12Z",
                    "https://arstechnica.com/tech-policy/2021/03/i-was-a-teenage-twitter-hacker-graham-ivan-clark-gets-3-year-sentence/"
                ),
                News(
                    "GS Acquisition Holdings Corp II: Rumors Create An Opportunity",
                    "There are rumors swirling around involving BlockFi and Flipkart. The excitement does create opportunity to position in GSAH that was valued at $15 pre-deal just weeks ago.",
                    "https://static3.seekingalpha.com/assets/og_image_192-59bfd51c9fe6af025b2f9f96c807e46f8e2f06c5ae787b15bf1423e6c676d4db.png",
                    "Yesterday there was a lot of price action in Goldman Sachs Acquisition Company II (NYSE:GSAH). This is a SPAC with a sponsor controlled by an affiliate of Goldman Sachs (NYSE:GS) through its Permanen… [+6435 chars] Yesterday there was a lot of price action in Goldman Sachs Acquisition Company II (NYSE:GSAH). This is a SPAC with a sponsor controlled by an affiliate of Goldman Sachs (NYSE:GS) through its Permanen… [+6435 chars]",
                    "Bram de Haas",
                    "Seeking Alpha",
                    "2021-03-12T11:38:12Z",
                    "https://arstechnica.com/tech-policy/2021/03/i-was-a-teenage-twitter-hacker-graham-ivan-clark-gets-3-year-sentence/"
                )
            )


        val adapter = NewsListAdapter(this)
        adapter.addText("Cabeçalho")
        adapter.addData(newsList) { news ->
            val navigateToDetailsIntent = Intent(this, NewsDetailActivity::class.java)
            navigateToDetailsIntent.putExtra(NewsDetailActivity.NEWS_DETAIL_KEY, news)
            startActivity(navigateToDetailsIntent)
        }
        adapter.addText("Rodapé")

        binding.recyclerviewNews.adapter = adapter
        binding.recyclerviewNews.layoutManager = LinearLayoutManager(this)

    }


    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelableArrayList(NEWS_KEY, newsList)
    }


}