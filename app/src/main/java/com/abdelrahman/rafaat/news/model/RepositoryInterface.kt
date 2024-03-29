package com.abdelrahman.rafaat.news.model

import retrofit2.Response

interface RepositoryInterface {

    suspend fun getNewsBYCountry(category: String, page: Int): Response<NewsModel>

    suspend fun getNewsBySearch(searchTopic: String, page: Int): Response<NewsModel>

}