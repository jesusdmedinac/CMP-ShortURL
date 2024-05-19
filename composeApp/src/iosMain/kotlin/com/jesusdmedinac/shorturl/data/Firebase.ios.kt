package com.jesusdmedinac.shorturl.data

import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.FirebaseOptions
import dev.gitlive.firebase.initialize

actual fun initFirebase(context: Any?, options: FirebaseOptions) {
    Firebase.initialize()
}