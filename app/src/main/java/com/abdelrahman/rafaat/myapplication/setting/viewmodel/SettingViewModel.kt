package com.abdelrahman.rafaat.myapplication.setting.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abdelrahman.rafaat.myapplication.model.*
import kotlinx.coroutines.launch


private const val TAG = "SettingViewModel"

class SettingViewModel(private val _iRepo: RepositoryInterface) : ViewModel() {


    init {
        Log.i(TAG, "init: ")

    }

    fun updateSharedPreference(country: String, sortBy: String) {
        Log.i(TAG, "updateSharedPreference: ")
        viewModelScope.launch {
            Log.i(TAG, "updateSharedPreference: country-------------> $country")
            Log.i(TAG, "updateSharedPreference: sortBy--------------> $sortBy")
            _iRepo.updateSharedPreference(country, sortBy)
        }

    }

    override fun onCleared() {
        super.onCleared()
        Log.i(TAG, "onCleared: ")
    }

}