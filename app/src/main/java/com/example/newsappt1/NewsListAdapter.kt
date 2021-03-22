package com.example.newsappt1

import android.content.Context
import android.view.View
import com.bumptech.glide.Glide
import com.example.newsappt1.databinding.NewsItemBinding
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.viewbinding.BindableItem

class NewsListAdapter(val context: Context, val dataSet: List<News>): GroupAdapter<GroupieViewHolder>() {

    init {
        clear()
        dataSet.forEach {
            add(NewsItem())
        }
    }

    inner class NewsItem : BindableItem<NewsItemBinding>() {
        override fun bind(viewBinding: NewsItemBinding, position: Int) {
            val news = dataSet[position]

            viewBinding.titleNewsItem.text = news.title

            Glide
                .with(context)
                .load(news.imageUrl)
                .placeholder(R.drawable.ic_no_image)
                .into(viewBinding.imgNewsItem)

        }

        override fun getLayout(): Int = R.layout.news_item

        override fun initializeViewBinding(view: View): NewsItemBinding = NewsItemBinding.bind(view)

    }

}