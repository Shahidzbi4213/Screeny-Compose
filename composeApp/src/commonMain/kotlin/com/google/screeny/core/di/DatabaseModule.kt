package com.google.screeny.core.di

import androidx.room.RoomDatabase
import com.google.screeny.data.local.ScreenyDatabase
import com.google.screeny.data.local.dao.FavouriteWallpaperDao
import com.google.screeny.data.local.getRoomDatabase
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single

@Module
class DatabaseModule {


    @Single
    fun provideDatabase(builder: RoomDatabase.Builder<ScreenyDatabase>): ScreenyDatabase {
        return getRoomDatabase(builder)
    }

    @Single
    fun provideFavouriteWallpaperDao(db: ScreenyDatabase): FavouriteWallpaperDao {
        return db.favouriteWallpaperDao()

    }
}