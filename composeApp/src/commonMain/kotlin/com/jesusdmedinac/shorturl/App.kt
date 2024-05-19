package com.jesusdmedinac.shorturl

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.Navigator
import com.jesusdmedinac.shorturl.presentation.ui.screen.ShortURLScreen

@Composable
fun App() {
    MaterialTheme {
        Navigator(ShortURLScreen)
    }
}
