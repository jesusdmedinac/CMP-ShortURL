package com.jetbrains.kmpapp

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.Navigator
import com.jetbrains.kmpapp.shorturl.presentation.ui.screen.ShortURLScreen

@Composable
fun App() {
    MaterialTheme {
        Navigator(ShortURLScreen)
    }
}
