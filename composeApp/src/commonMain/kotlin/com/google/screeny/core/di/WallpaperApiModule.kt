package com.google.screeny.core.di

import com.google.screeny.data.remote.PexelWallpapersApi
import com.google.screeny.data.remote.PexelWallpapersApiImpl
import io.ktor.client.HttpClient
import org.koin.core.annotation.Factory
import org.koin.core.annotation.Module

@Module
class WallpaperApiModule {

    @Factory(binds = [PexelWallpapersApi::class])
    fun provideWallpaperApi(
        httpClient: HttpClient
    ): PexelWallpapersApi = PexelWallpapersApiImpl(httpClient = httpClient)
}