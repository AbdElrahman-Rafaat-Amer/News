package com.abdelrahman.rafaat.news.network

import com.abdelrahman.rafaat.news.model.NewsModel
import retrofit2.Response

class NewsClient private constructor() : RemoteSource {

    private val newsHelper = NewsHelper.getClient

    override suspend fun getNewsByCountry(
        country: String, category: String, sortBy: String, page: Int
    ): Response<NewsModel> {

        val response = newsHelper.getNewsBYCountry(
            country = country,
            category = category,
            sortBy = sortBy,
            page = page.toString()
        )
        return response
    }


    override suspend fun getNewsBySearch(
        searchTopic: String,
        sortBy: String,
        page: Int
    ): Response<NewsModel> {
        val response =
            newsHelper.getNews(
                q = searchTopic,
                sortBy = sortBy,
                page = page.toString()
            )
        return response
    }

    companion object {
        private var instance: NewsClient? = null
        fun getNewsClient(): NewsClient {
            if (instance == null)
                instance = NewsClient()
            return instance!!
        }
    }

}