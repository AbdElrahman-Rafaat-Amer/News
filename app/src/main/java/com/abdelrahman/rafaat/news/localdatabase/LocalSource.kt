package com.abdelrahman.rafaat.news.localdatabase

import android.content.Context

class LocalSource private constructor(context: Context) : LocalSourceInterface {

    private var newsDao: NewsDao? = null

    init {
        val db: NewsDatabase = NewsDatabase.getDatabaseClient(context.applicationContext)
        newsDao = db.newsDao()
    }

    override fun insertNews(news: NewsEntity) {
        newsDao?.insertNews(news)
    }

    override fun getAllNews(): List<NewsEntity>? {
        return newsDao?.getNewsFromDatabase()
    }

    override fun deleteNews(news: NewsEntity): Int? {
        return newsDao?.deleteNews(news)
    }

    companion object{
        private var localSource: LocalSource? = null
        fun getInstance(context: Context): LocalSource {
            if (localSource == null) {
                localSource = LocalSource(context)
            }
            return localSource!!
        }
    }
}