package com.abdelrahman.rafaat.myapplication.model

import retrofit2.Response

interface RepositoryInterface {

    fun updateSharedPreference(country: String, sortBy: String)

    suspend fun getNewsBYCountry(category: String, page: Int): Response<NewsModel>

    suspend fun getNewsBySearch(searchTopic: String, page: Int): Response<NewsModel>

}