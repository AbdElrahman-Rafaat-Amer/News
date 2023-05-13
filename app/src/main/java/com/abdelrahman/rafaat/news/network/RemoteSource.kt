package com.abdelrahman.rafaat.news.network

import com.abdelrahman.rafaat.news.model.NewsModel
import retrofit2.Response

interface RemoteSource {

    suspend fun getNewsBYCountry(
        country: String,
        category: String,
        sortBy: String,
        page: Int
    ): Response<NewsModel>


    suspend fun getNewsBySearch(
        searchTopic: String,
        sortBy: String,
        page: Int
    ): Response<NewsModel>
}