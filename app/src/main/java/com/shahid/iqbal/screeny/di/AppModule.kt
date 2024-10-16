package com.shahid.iqbal.screeny.di

import com.shahid.iqbal.screeny.utils.WallpaperDownloader
import com.shahid.iqbal.screeny.utils.WallpaperManager
import org.koin.dsl.module


val appModule = module {

    single { WallpaperDownloader(get()) }
    single { WallpaperManager(get()) }
}
