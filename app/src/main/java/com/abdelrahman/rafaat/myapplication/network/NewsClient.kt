package com.abdelrahman.rafaat.myapplication.network

import android.util.Log
import com.abdelrahman.rafaat.myapplication.model.NewsModel
import retrofit2.Response

private const val TAG = "NewsClient"

class NewsClient private constructor() : RemoteSource {

    private val newsHelper = NewsHelper.getClient

    override suspend fun getNewsBYCountry(
        country: String, category: String, sortBy: String, page: Int
    ): Response<NewsModel> {

        val response = newsHelper.getNewsBYCountry(
            country = country,
            category = category,
            sortBy = sortBy,
            page = page.toString()
        )
        Log.i(TAG, "getNewsBYCountry: code----------> ${response.code()}")
        Log.i(TAG, "getNewsBYCountry: body----------> ${response.body()}")

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

        Log.i(TAG, "getNews: code----------> ${response.code()}")
        Log.i(TAG, "getNews: body----------> ${response.body()}")
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