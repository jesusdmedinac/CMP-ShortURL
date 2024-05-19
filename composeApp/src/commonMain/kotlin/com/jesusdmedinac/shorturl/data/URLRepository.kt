package com.jesusdmedinac.shorturl.data

import com.jesusdmedinac.shorturl.utils.uuid
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.firestore.firestore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.datetime.Clock

class URLRepository {
    fun loadURLs(): Flow<List<DataShortenedURL>> = Firebase
        .firestore
        .collection("urls")
        .snapshots
        .map { snapshot ->
            snapshot
                .documents
                .map { documentSnapshot ->
                    documentSnapshot.data<DataShortenedURL>()
                }
        }
    suspend fun shortenURL(url: String): DataShortenedURL {
        val shortUUID = uuid()
        val long = url
        val short = "https://shorturl-cc54f.web.app/$shortUUID"
        val dataShortenedURL = DataShortenedURL(
            long,
            short,
            0,
            Clock.System.now().toEpochMilliseconds()
        )
        Firebase
            .firestore
            .collection("urls")
            .document(shortUUID)
            .set(dataShortenedURL)

        return dataShortenedURL
    }
}