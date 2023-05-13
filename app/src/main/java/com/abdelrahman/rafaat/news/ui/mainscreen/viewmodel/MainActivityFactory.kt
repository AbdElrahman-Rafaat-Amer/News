package com.abdelrahman.rafaat.news.ui.mainscreen.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.abdelrahman.rafaat.news.model.RepositoryInterface
import java.lang.IllegalArgumentException

class MainActivityFactory(
    private val _iRepo: RepositoryInterface,
    private val application: Application
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(MainActivityViewModel::class.java)) {
            MainActivityViewModel(_iRepo, application) as T
        } else {
            throw IllegalArgumentException("ViewModel Class not found")
        }
    }
}