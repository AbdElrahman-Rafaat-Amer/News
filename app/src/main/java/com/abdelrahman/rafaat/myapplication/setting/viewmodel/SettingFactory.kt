package com.abdelrahman.rafaat.myapplication.setting.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.abdelrahman.rafaat.myapplication.model.RepositoryInterface
import java.lang.IllegalArgumentException

class SettingFactory(private val _iRepo: RepositoryInterface) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(SettingViewModel::class.java)) {
            SettingViewModel(_iRepo) as T
        } else {
            throw IllegalArgumentException("ViewModel Class not found")
        }
    }
}