package com.abdelrahman.rafaat.news.ui.mainscreen.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abdelrahman.rafaat.news.model.Article
import com.abdelrahman.rafaat.news.model.NewsModel
import com.abdelrahman.rafaat.news.model.RepositoryInterface
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class MainActivityViewModel(private val _iRepo: RepositoryInterface, var application: Application) :
    ViewModel() {
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

    private var _savedNews = MutableLiveData<List<Article>>()
    val savedNews: LiveData<List<Article>> = _savedNews

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

    fun saveNews(article: Article) {
        viewModelScope.launch {
            executeIOOperation {
                _iRepo.saveNews(article)
            }
        }
    }

    fun getAllSavedNews() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val savedNews = _iRepo.getSavedNews()
                _savedNews.postValue(savedNews)
            }
        }
    }

    fun deleteNews(article: Article) {
        viewModelScope.launch {
            executeIOOperation {
                _iRepo.deleteNews(article)
            }
        }
    }

    private suspend fun executeIOOperation(ioOperation: suspend () -> Unit) {
        withContext(Dispatchers.IO) {
            ioOperation()
        }
    }
}