package com.google.screeny.core.platform

import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.engine.okhttp.OkHttpConfig
import io.ktor.client.engine.okhttp.OkHttpEngine

actual class HttpEngineFactory {
    actual fun getHttpClientEngine(): HttpClientEngine {
        return OkHttp.create()
    }
}