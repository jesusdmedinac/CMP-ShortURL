package com.jesusdmedinac.shorturl.utils

import platform.Foundation.NSUUID

actual fun uuid(): String = NSUUID.UUID().UUIDString