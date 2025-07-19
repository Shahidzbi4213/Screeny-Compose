package com.shahid.iqbal.screeny.di

import com.shahid.iqbal.screeny.BuildConfig
import com.shahid.iqbal.screeny.NativeKeyProvider
import com.shahid.iqbal.screeny.utils.Extensions.debug
import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.header
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.dsl.module

const val TIME_OUT = 10_00_0

val networkModule = module {

    single {

        HttpClient(Android) {

            engine {
                connectTimeout = TIME_OUT
                socketTimeout = TIME_OUT
            }

            install(ContentNegotiation) {
                json(Json {
                    prettyPrint = true
                    isLenient = true
                    ignoreUnknownKeys = true
                })

            }

            if (BuildConfig.DEBUG) {
                install(Logging) {
                    logger = object : Logger {
                        override fun log(message: String) {
                            message.debug()
                        }
                    }
                }
            }


            install(DefaultRequest) {
                header(HttpHeaders.ContentType, ContentType.Application.Json)
                header(HttpHeaders.Authorization, NativeKeyProvider.getApiKey())
            }

        }
    }
}