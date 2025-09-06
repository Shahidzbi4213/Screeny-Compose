package com.google.screeny.core.di

import com.google.screeny.data.local.ScreenyDatabase
import com.google.screeny.data.local.dao.FavouriteWallpaperDao
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single

@Module
class DatabaseModule {

    @Single
    fun provideFavouriteWallpaperDao(db: ScreenyDatabase): FavouriteWallpaperDao{
        return db.favouriteWallpaperDao()

    }
}