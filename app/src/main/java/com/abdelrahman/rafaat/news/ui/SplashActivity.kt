package com.abdelrahman.rafaat.news.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.abdelrahman.rafaat.news.R
import com.abdelrahman.rafaat.news.ui.mainscreen.view.MainActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        CoroutineScope(Dispatchers.Main).launch {
            delay(5000)
            startActivity(Intent(this@SplashActivity, MainActivity::class.java))
            finish()
        }

    }
}