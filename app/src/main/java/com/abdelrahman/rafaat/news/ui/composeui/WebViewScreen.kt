package com.abdelrahman.rafaat.news.ui.composeui

import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.OnBackPressedCallback
import androidx.activity.OnBackPressedDispatcherOwner
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView

//@Composable
//fun WebViewScreen(url: String) {
//    AndroidView(
//        modifier = Modifier.fillMaxSize(),
//        factory = {
//            WebView(it).apply {
//                this.loadUrl(url)
//                this.webViewClient = WebViewClient()
//                settings.javaScriptEnabled = true
//            }
//        }
//    )
//}

@Composable
fun WebViewWithBackButtonHandling(
    url: String,
    onBackPressed: () -> Unit
) {
    val context = LocalContext.current

    // Create a WebView instance
    val webView = remember {
        WebView(context).apply {
            this.webViewClient = WebViewClient()
            this.loadUrl(url)
            this.settings.javaScriptEnabled = true

        }
    }

    val backCallback = remember {
        object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (webView.canGoBack()) {
                    webView.goBack()
                } else {
                    onBackPressed()
                }
            }
        }
    }

    DisposableEffect(webView) {
        val onBackPressedDispatcher = (context as? OnBackPressedDispatcherOwner)?.onBackPressedDispatcher
        onBackPressedDispatcher?.addCallback(backCallback)
        onDispose {
            backCallback.remove()
        }
    }

    AndroidView(
        modifier = Modifier.fillMaxSize(),
        factory = { webView }
    )
}