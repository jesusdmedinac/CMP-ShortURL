package com.jesusdmedinac.shorturl

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.window.ComposeUIViewController
import com.jesusdmedinac.shorturl.data.initFirebase

fun MainViewController() = ComposeUIViewController {
    LaunchedEffect(Unit) {
        initFirebase()
    }
    App()
}
