package com.google.screeny.core.platform

import io.ktor.client.engine.HttpClientEngine

expect class HttpEngineFactory() {

    fun getHttpClientEngine(): HttpClientEngine
}