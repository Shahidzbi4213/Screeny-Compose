package com.google.screeny.core.platform

import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.engine.darwin.Darwin

actual class HttpEngineFactory {
    actual fun getHttpClientEngine(): HttpClientEngine {
        return Darwin.create()
    }
}