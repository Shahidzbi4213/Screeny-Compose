package com.shahid.iqbal.screeny.di

import com.shahid.iqbal.screeny.data.remote.PexelWallpapersApi
import com.shahid.iqbal.screeny.data.remote.PexelWallpapersApiImpl
import org.koin.dsl.module


val wallpaperApiModule = module {
    single<PexelWallpapersApi> { PexelWallpapersApiImpl(get()) }
}