package com.abdelrahman.rafaat.news.localdatabase

import androidx.lifecycle.LiveData

interface LocalSourceInterface {
    fun insertNews(news: NewsEntity)
    fun getAllNews(): List<NewsEntity>?
    fun deleteNews(news: NewsEntity): Int?
}