package com.example.newsappt1

import android.content.Context
import android.view.View
import com.bumptech.glide.Glide
import com.example.newsappt1.databinding.NewsItemBinding
import com.example.newsappt1.databinding.TextItemBinding
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.viewbinding.BindableItem

class NewsListAdapter(val context: Context): GroupAdapter<GroupieViewHolder>() {

    fun addText(text: String, clickListener: (String) -> Unit) {
        add(TextItem(text, clickListener))
    }

    fun addData(newsList: List<News>, clickListener: (News) -> Unit) {
        newsList.forEach { news ->
            add(NewsItem(news, clickListener))
        }
    }

    inner class NewsItem(val news: News, val clickListener: (News) -> Unit) : BindableItem<NewsItemBinding>() {
        override fun bind(viewBinding: NewsItemBinding, position: Int) {

            viewBinding.titleNewsItem.text = news.title

            Glide
                .with(context)
                .load(news.imageUrl)
                .placeholder(R.drawable.ic_no_image)
                .into(viewBinding.imgNewsItem)

            viewBinding.root.setOnClickListener {
                clickListener(news)
            }

        }

        override fun getLayout(): Int = R.layout.news_item

        override fun initializeViewBinding(view: View): NewsItemBinding = NewsItemBinding.bind(view)

    }

    inner class TextItem(private val text: String, private val clickListener: (String) -> Unit): BindableItem<TextItemBinding>() {

        override fun bind(viewBinding: TextItemBinding, position: Int) {
            viewBinding.txtItemText.text = text

            viewBinding.root.setOnClickListener {
                clickListener(text)
            }
        }

        override fun getLayout(): Int = R.layout.text_item

        override fun initializeViewBinding(view: View): TextItemBinding = TextItemBinding.bind(view)

    }

}