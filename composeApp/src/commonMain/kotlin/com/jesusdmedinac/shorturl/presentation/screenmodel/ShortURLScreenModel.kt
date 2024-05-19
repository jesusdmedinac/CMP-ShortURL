package com.jesusdmedinac.shorturl.presentation.screenmodel

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.container
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.reduce

class ShortURLScreenModel : ScreenModel, ContainerHost<ShortURLState, ShortURLSideEffect> {
    override val container: Container<ShortURLState, ShortURLSideEffect> =
        screenModelScope.container(ShortURLState())

    fun shortenURL(url: String) = intent {
        // TODO Call shortenURL UseCase
        reduce {
            state.copy(shortenURLs = state.shortenURLs + ShortenURL(url, url))
        }
    }
}

data class ShortURLState(
    val shortenURLs: List<ShortenURL> = emptyList(),
)

data class ShortenURL(
    val url: String,
    val shortURL: String,
)

sealed class ShortURLSideEffect