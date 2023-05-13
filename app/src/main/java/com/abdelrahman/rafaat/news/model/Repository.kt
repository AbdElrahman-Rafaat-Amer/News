package com.abdelrahman.rafaat.news.model

import android.app.Application
import android.util.Log
import androidx.preference.PreferenceManager
import com.abdelrahman.rafaat.news.network.RemoteSource
import retrofit2.Response

private const val TAG = "Repository"

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

        Log.i(TAG, "checkSharedPreference: country----------> $country")
        Log.i(TAG, "checkSharedPreference: sortBY----------> $sortBy")
    }

    override suspend fun getNewsBYCountry(category: String, page: Int): Response<NewsModel> {
        checkSharedPreference()
        val response = this.remoteSource.getNewsBYCountry(
            country = country,
            sortBy = sortBy,
            category = category,
            page = page
        )
        Log.i(TAG, "getNewsBYCountry: code-----------> ${response.code()}")
        Log.i(TAG, "getNewsBYCountry: status----------> ${response.body()?.status}")
        Log.i(TAG, "getNewsBYCountry: status----------> ${response.body()?.articles!!.size}")

        return response
    }

    override suspend fun getNewsBySearch(searchTopic: String, page: Int): Response<NewsModel> {
        checkSharedPreference()
        val response = remoteSource.getNewsBySearch(searchTopic, sortBy, page)
        Log.i(TAG, "getNews: code------------> ${response.code()}")
        Log.i(TAG, "getNews: status----------> ${response.body()?.status}")
        return response
    }
}