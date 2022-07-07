package com.abdelrahman.rafaat.myapplication.network

import com.abdelrahman.rafaat.myapplication.model.NewsModel
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