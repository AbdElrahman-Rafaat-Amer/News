package com.abdelrahman.rafaat.myapplication.model

import android.content.SharedPreferences
import android.util.Log
import com.abdelrahman.rafaat.myapplication.network.RemoteSource
import retrofit2.Response

private const val TAG = "Repository"

class Repository private constructor(
    private var remoteSource: RemoteSource,
    private var sharedPreferences: SharedPreferences
) : RepositoryInterface {

    private lateinit var sortBy: String
    private lateinit var country: String
    private var editor: SharedPreferences.Editor? = null

    companion object {
        private var instance: Repository? = null
        fun getNewsClient(
            remoteSource: RemoteSource,
            sharedPreferences: SharedPreferences
        ): Repository {
            if (instance == null)
                instance = Repository(remoteSource, sharedPreferences)

            return instance!!
        }
    }

    init {
        Log.i(TAG, "init: ")
        this.editor = sharedPreferences.edit()
        getDefaultData()
    }

    private fun getDefaultData() {
        country = sharedPreferences.getString("country", "us")!!
        sortBy = sharedPreferences.getString("sortBy", "publishedAt")!!

        Log.i(TAG, "getDefaultData: country------------------> $country")
        Log.i(TAG, "getDefaultData: sortBy-------------------> $sortBy")
    }

    override fun updateSharedPreference(country: String, sortBy: String) {
        this.sortBy = sortBy
        this.country = country
        editor?.putString("country", country)
        editor?.putString("sortBy", sortBy)
        editor?.commit()

        Log.i(TAG, "updateSharedPreference: country------------------> $country")
        Log.i(TAG, "updateSharedPreference: sortBy-------------------> $sortBy")
    }


    override suspend fun getNewsBYCountry(
        category: String, page: Int
    ): Response<NewsModel> {

        val response = this.remoteSource.getNewsBYCountry(
            country = country,
            sortBy = sortBy,
            category = category,
            page = page
        )
        Log.i(TAG, "getNewsBYCountry: code-----------> ${response.code()}")
        Log.i(TAG, "getNewsBYCountry: status----------> ${response.body()?.status}")

        return response
    }

    override suspend fun getNewsBySearch(searchTopic: String, page: Int): Response<NewsModel> {
        val response = remoteSource.getNewsBySearch(searchTopic, sortBy, page)
        Log.i(TAG, "getNews: code------------> ${response.code()}")
        Log.i(TAG, "getNews: status----------> ${response.body()?.status}")
        return response
    }
}