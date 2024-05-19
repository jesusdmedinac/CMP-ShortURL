package com.jesusdmedinac.shorturl.utils

import java.util.UUID

actual fun uuid(): String = UUID.randomUUID().toString()