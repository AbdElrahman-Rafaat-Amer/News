package com.abdelrahman.rafaat.myapplication.ui.mainscreen.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abdelrahman.rafaat.myapplication.model.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

private const val TAG = "MainActivityModel"

class MainActivityViewModel(private val _iRepo: RepositoryInterface, var application: Application) : ViewModel() {
    private var _news = MutableLiveData<NewsModel>()
    val news: LiveData<NewsModel> = _news

    private var _sport = MutableLiveData<NewsModel>()
    val sport: LiveData<NewsModel> = _sport

    private var _science = MutableLiveData<NewsModel>()
    val science: LiveData<NewsModel> = _science

    private var _health = MutableLiveData<NewsModel>()
    val health: LiveData<NewsModel> = _health

    private var _business = MutableLiveData<NewsModel>()
    val business: LiveData<NewsModel> = _business

    private var _entertainment = MutableLiveData<NewsModel>()
    val entertainment: LiveData<NewsModel> = _entertainment

   /* private var country = PreferenceManager.getDefaultSharedPreferences(application.applicationContext)
        .getString("country", "us")!!

    private var sortBy = PreferenceManager.getDefaultSharedPreferences(application.applicationContext)
        .getString("country", "us")!!*/

    init {
        Log.i(TAG, "init: ")
    }

   /* fun checkSharedPreference(){
        country = PreferenceManager.getDefaultSharedPreferences(application.applicationContext)
            .getString("country", "us")!!

        sortBy = PreferenceManager.getDefaultSharedPreferences(application.applicationContext)
            .getString("country", "us")!!
    }*/

    fun getNewsBySearch(page: Int, searchTopic: String) {
        viewModelScope.launch {
            val response =
                _iRepo.getNewsBySearch(
                    searchTopic,
                    page
                )
            withContext(Dispatchers.Main) {
                if (response.code() == 200 && response.body()?.status == "ok") {
                    _news.postValue(response.body())
                } else {
                    _news.postValue(NewsModel(emptyList(), "Error", -1))
                }
            }
        }

    }

    fun getNews(page: Int) {
        viewModelScope.launch {
            val response = _iRepo.getNewsBYCountry("general", page)
            Log.i(TAG, "getNews: code---------------> ${response.code()}")
            Log.i(TAG, "getNews: status-------------> ${response.body()?.status}")
            Log.i(TAG, "getNews: size---------------> ${response.body()?.articles?.size}")
            withContext(Dispatchers.Main) {
                if (response.code() == 200 && response.body()?.status == "ok") {
                    _news.postValue(response.body())
                } else {
                    _news.postValue(NewsModel(emptyList(), "Error", -1))
                }
            }
        }

    }

    fun getSport(page: Int) {
        viewModelScope.launch {
            val response = _iRepo.getNewsBYCountry("sport", page)
            withContext(Dispatchers.Main) {
                if (response.code() == 200 && response.body()?.status == "ok") {
                    _sport.postValue(response.body())
                } else {
                    _sport.postValue(NewsModel(emptyList(), "Error", -1))
                }
            }
        }

    }

    fun getScience(page: Int) {
        viewModelScope.launch {
            val response = _iRepo.getNewsBYCountry("science", page)
            withContext(Dispatchers.Main) {
                if (response.code() == 200 && response.body()?.status == "ok") {
                    _science.postValue(response.body())
                } else {
                    _science.postValue(NewsModel(emptyList(), "Error", -1))
                }
            }
        }

    }

    fun getHealth(page: Int) {
        viewModelScope.launch {
            val response = _iRepo.getNewsBYCountry("health", page)
            withContext(Dispatchers.Main) {
                if (response.code() == 200 && response.body()?.status == "ok") {
                    _health.postValue(response.body())
                } else {
                    _health.postValue(NewsModel(emptyList(), "Error", -1))
                }
            }
        }

    }

    fun getBusiness(page: Int) {
        viewModelScope.launch {
            val response = _iRepo.getNewsBYCountry("business", page)
            withContext(Dispatchers.Main) {
                if (response.code() == 200 && response.body()?.status == "ok") {
                    _business.postValue(response.body())
                } else {
                    _business.postValue(NewsModel(emptyList(), "Error", -1))
                }
            }
        }

    }

    fun getEntertainment(page: Int) {
        viewModelScope.launch {
            val response = _iRepo.getNewsBYCountry("entertainment", page)
            withContext(Dispatchers.Main) {
                if (response.code() == 200 && response.body()?.status == "ok") {
                    _entertainment.postValue(response.body())
                } else {
                    _entertainment.postValue(NewsModel(emptyList(), "Error", -1))
                }
            }
        }

    }

    override fun onCleared() {
        super.onCleared()
        Log.i(TAG, "onCleared: ")
    }
}