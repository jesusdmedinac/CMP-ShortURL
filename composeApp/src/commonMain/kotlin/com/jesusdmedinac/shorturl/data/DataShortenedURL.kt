package com.jesusdmedinac.shorturl.data

import kotlinx.serialization.Serializable

@Serializable
data class DataShortenedURL(
    val long: String,
    val short: String,
    val timesOpened: Int,
    val createdAt: Long
)
