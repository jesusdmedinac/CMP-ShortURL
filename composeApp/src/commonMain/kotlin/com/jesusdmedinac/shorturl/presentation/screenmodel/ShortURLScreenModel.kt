package com.jesusdmedinac.shorturl.presentation.screenmodel

import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.jesusdmedinac.shorturl.data.URLRepository
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.container
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.reduce

class ShortURLScreenModel(
    private val urlRepository: URLRepository
) : ScreenModel, ContainerHost<ShortURLState, ShortURLSideEffect> {
    override val container: Container<ShortURLState, ShortURLSideEffect> =
        screenModelScope.container(ShortURLState())

    fun loadShortenURLs() = intent {
        urlRepository
            .loadURLs()
            .collect { shortenedURLs ->
                val shortenURLs = shortenedURLs.map {
                    ShortenURL(
                        long = it.long,
                        short = it.short
                    )
                }
                reduce {
                    state.copy(shortenURLs = shortenURLs)
                }
            }
    }

    fun shortenURL(url: String) = intent {
        urlRepository.shortenURL(url)
    }
}

data class ShortURLState(
    val shortenURLs: List<ShortenURL> = emptyList(),
)

data class ShortenURL(
    val long: String,
    val short: String,
)

sealed class ShortURLSideEffect