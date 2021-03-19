package com.example.newsappt1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.newsappt1.databinding.ActivityNewsListBinding

class NewsListActivity : AppCompatActivity() {

    lateinit var binding: ActivityNewsListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityNewsListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnNavigationTest.setOnClickListener {
            val navigateToMainActivity = Intent(this, NewsDetailActivity::class.java)
            startActivity(navigateToMainActivity)
        }
    }
}