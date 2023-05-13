package com.abdelrahman.rafaat.news.model

import android.app.Application
import androidx.preference.PreferenceManager
import com.abdelrahman.rafaat.news.network.RemoteSource
import retrofit2.Response

class Repository private constructor(
    private var remoteSource: RemoteSource, var application: Application
) : RepositoryInterface {

    private var country =
        PreferenceManager.getDefaultSharedPreferences(application.applicationContext)
            .getString("country", "us")!!

    private var sortBy =
        PreferenceManager.getDefaultSharedPreferences(application.applicationContext)
            .getString("sortBY", "publishedAt")!!

    companion object {
        private var instance: Repository? = null
        fun getNewsClient(remoteSource: RemoteSource, application: Application): Repository {
            if (instance == null)
                instance = Repository(remoteSource, application)

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
}