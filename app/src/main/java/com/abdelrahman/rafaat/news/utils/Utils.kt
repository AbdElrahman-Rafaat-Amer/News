package com.abdelrahman.rafaat.news.utils

import android.content.Context
import android.content.Intent
import android.os.Build
import android.provider.Settings

fun connectInternet(context: Context) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        context.startActivity(Intent(Settings.Panel.ACTION_INTERNET_CONNECTIVITY))
    } else {
        context.startActivity(Intent(Settings.ACTION_WIFI_SETTINGS))
    }
}