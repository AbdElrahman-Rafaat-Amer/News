package com.abdelrahman.rafaat.news.network

import com.abdelrahman.rafaat.news.model.NewsModel
import com.abdelrahman.rafaat.news.utils.NEWS_API_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsService {

    @GET("top-headlines")
    suspend fun getNewsBYCountry(
        @Query("country") country: String = "",
        @Query("category") category: String = "general",
        @Query("sortBy") sortBy: String = "publishedAt",
        @Query("page") page: String = "1",
        @Query("apiKey") apiKey: String = NEWS_API_KEY
    ): Response<NewsModel>

    @GET("everything")
    suspend fun getNews(
        @Query("q") q: String,
        @Query("sortBy") sortBy: String = "publishedAt",
        @Query("page") page: String = "1",
        @Query("apiKey") apiKey: String = NEWS_API_KEY
    ): Response<NewsModel>
}