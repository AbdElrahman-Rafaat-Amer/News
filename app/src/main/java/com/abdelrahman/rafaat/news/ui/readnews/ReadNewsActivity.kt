package com.abdelrahman.rafaat.news.ui.readnews

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.abdelrahman.rafaat.news.ui.composeui.WebViewWithBackButtonHandling

class ReadNewsActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val url = intent.getStringExtra("NEWS_URL") ?: ""
        setContent {
            WebViewWithBackButtonHandling(
                url = url
            ) {
                finish()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ReadingNewsPreview() {
    WebViewWithBackButtonHandling(
        url = "https://developer.android.com/develop/ui/views/layout/webapps/webview"
    ) {

    }
}