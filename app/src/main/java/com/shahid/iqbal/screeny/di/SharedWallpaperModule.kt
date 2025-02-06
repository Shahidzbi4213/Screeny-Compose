package com.shahid.iqbal.screeny.di

import coil.ImageLoader
import coil.request.CachePolicy
import com.shahid.iqbal.screeny.data.repositories.FavouriteRepo
import com.shahid.iqbal.screeny.data.repositories.RecentSearchRepository
import com.shahid.iqbal.screeny.data.repositories.SearchWallpapersRepository
import com.shahid.iqbal.screeny.data.repositories.UserPreferenceRepo
import com.shahid.iqbal.screeny.data.repositories.WallpaperRepository
import okhttp3.OkHttpClient
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import java.util.concurrent.TimeUnit

val sharedWallpaperModule = module {

    singleOf(::WallpaperRepository)
    singleOf(::SearchWallpapersRepository)
    singleOf(::RecentSearchRepository)
    singleOf(::FavouriteRepo)
    singleOf(::UserPreferenceRepo)

    single<ImageLoader> {
        ImageLoader.Builder(get())
            .crossfade(true)
            .crossfade(100)
            .diskCachePolicy(CachePolicy.ENABLED)
            .memoryCachePolicy(CachePolicy.DISABLED)
            .okHttpClient(
                OkHttpClient.Builder()
                    .callTimeout(10L, TimeUnit.SECONDS)
                    .build()
            ).build()
    }
}