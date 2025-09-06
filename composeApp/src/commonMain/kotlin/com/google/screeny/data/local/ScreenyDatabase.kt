package com.google.screeny.data.local

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor
import com.google.screeny.data.local.dao.FavouriteWallpaperDao
import com.google.screeny.data.local.entities.FavouriteWallpaperEntity

@Database(entities = [FavouriteWallpaperEntity::class], version = 1)
@ConstructedBy(AppDatabaseConstructor::class)
abstract class ScreenyDatabase : RoomDatabase() {

    abstract fun favouriteWallpaperDao(): FavouriteWallpaperDao

    companion object Companion {

        const val SCREENY_DATABASE = "screeny.db"
    }
}

// The Room compiler generates the `actual` implementations.
@Suppress("KotlinNoActualForExpect")
expect object AppDatabaseConstructor : RoomDatabaseConstructor<ScreenyDatabase> {
    override fun initialize(): ScreenyDatabase
}