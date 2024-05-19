package com.jesusdmedinac.shorturl

import android.app.Application
import com.jesusdmedinac.shorturl.data.initFirebase
import com.jesusdmedinac.shorturl.di.initKoin
import dev.gitlive.firebase.FirebaseOptions

class ShortURLApp : Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin()
        initFirebase(
            context = applicationContext,
            options = FirebaseOptions(
                applicationId = BuildKonfig.applicationId,
                apiKey = BuildKonfig.apiKey,
                projectId = BuildKonfig.projectId,
            )
        )
    }
}
