package com.abdelrahman.rafaat.news.model

import android.app.Application
import android.util.Log
import androidx.preference.PreferenceManager
import com.abdelrahman.rafaat.news.localdatabase.LocalSourceInterface
import com.abdelrahman.rafaat.news.network.RemoteSource
import com.abdelrahman.rafaat.news.utils.NewsMapper
import retrofit2.Response

class Repository private constructor(
    private var remoteSource: RemoteSource,
    private var localSource: LocalSourceInterface,
    var application: Application
) : RepositoryInterface {

    private var country =
        PreferenceManager.getDefaultSharedPreferences(application.applicationContext)
            .getString("country", "us")!!

    private var sortBy =
        PreferenceManager.getDefaultSharedPreferences(application.applicationContext)
            .getString("sortBY", "publishedAt")!!

    companion object {
        private var instance: Repository? = null
        fun getNewsClient(
            remoteSource: RemoteSource,
            localSource: LocalSourceInterface,
            application: Application
        ): Repository {
            if (instance == null)
                instance = Repository(remoteSource, localSource, application)

            return instance!!
        }
    }

    private fun checkSharedPreference() {
        country = PreferenceManager.getDefaultSharedPreferences(application.applicationContext)
            .getString("country", "us")!!

        sortBy = PreferenceManager.getDefaultSharedPreferences(application.applicationContext)
            .getString("sortBY", "publishedAt")!!
    }

    override suspend fun getNewsBYCountry(category: String, page: Int): Response<NewsModel> {
        checkSharedPreference()
        return remoteSource.getNewsByCountry(
            country = country,
            sortBy = sortBy,
            category = category,
            page = page
        )
    }

    override suspend fun getNewsBySearch(searchTopic: String, page: Int): Response<NewsModel> {
        checkSharedPreference()
        return remoteSource.getNewsBySearch(searchTopic, sortBy, page)
    }

    override suspend fun saveNews(article: Article) {
        Log.i("TAG", "saveNews:article $article")
        val entity = NewsMapper.toEntity(article)
        localSource.insertNews(entity)
    }

    override suspend fun getSavedNews(): List<Article> {
        val savedNews = localSource.getAllNews()
        return if (savedNews == null){
            emptyList()
        } else{
            NewsMapper.fromEntityList(savedNews)
        }
    }

    override suspend fun deleteNews(article: Article): Int? {
        val entity = NewsMapper.toEntity(article)
        return localSource.deleteNews(entity)
    }
}