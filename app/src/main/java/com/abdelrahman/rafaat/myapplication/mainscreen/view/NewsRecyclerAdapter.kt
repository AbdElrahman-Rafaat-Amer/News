package com.abdelrahman.rafaat.myapplication.mainscreen.view

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.abdelrahman.rafaat.myapplication.R
import com.abdelrahman.rafaat.myapplication.model.Article
import com.bumptech.glide.Glide

class NewsRecyclerAdapter : RecyclerView.Adapter<NewsRecyclerAdapter.ViewHolder>() {

    private var homeNews: List<Article> = emptyList()
    private lateinit var context: Context
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        val view =
            LayoutInflater.from(context).inflate(R.layout.custom_row, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val currentArticle = homeNews[position]

        Glide.with(context).load(currentArticle.urlToImage).centerCrop()
            .placeholder(R.drawable.ic_default_image).into(holder.newsImage)

        holder.sourceTextView.text = currentArticle.source.name
        holder.newsDataTextview.text = currentArticle.publishedAt.slice(0..9)
        holder.titleTextView.text = currentArticle.title
        holder.authorTextview.apply {
            if (currentArticle.author == null)
                this.text = context.resources.getString(R.string.unknown)
            else
                this.text = currentArticle.author
        }

        holder.descriptionTextview.text = currentArticle.description

        holder.itemView.setOnClickListener {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(currentArticle.url))
            context.startActivity(browserIntent)
        }

    }

    override fun getItemCount(): Int {
        return homeNews.size
    }

    fun setList(homeNews: List<Article>) {
        this.homeNews = homeNews
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var newsImage: ImageView = itemView.findViewById(R.id.news_imageView)
        var sourceTextView: TextView = itemView.findViewById(R.id.source_name_textview)
        var newsDataTextview: TextView = itemView.findViewById(R.id.news_data_textview)
        var titleTextView: TextView = itemView.findViewById(R.id.news_title_textview)
        var authorTextview: TextView = itemView.findViewById(R.id.author_textview)
        var descriptionTextview: TextView = itemView.findViewById(R.id.description_textview)
    }

}