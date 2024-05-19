package com.jesusdmedinac.shorturl.data

import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.FirebaseOptions
import dev.gitlive.firebase.initialize
import dev.gitlive.firebase.options

actual fun initFirebase(context: Any?, options: FirebaseOptions) {
    Firebase.initialize(context = context, options = options)
}