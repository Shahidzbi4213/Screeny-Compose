package com.google.screeny.data.local

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import com.google.screeny.data.local.dao.FavouriteWallpaperDao
import com.google.screeny.data.local.entities.FavouriteWallpaperEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO

@Database(entities = [FavouriteWallpaperEntity::class], version = 1, exportSchema = false)
@ConstructedBy(AppDatabaseConstructor::class)
abstract class ScreenyDatabase : RoomDatabase() {

    abstract fun favouriteWallpaperDao(): FavouriteWallpaperDao

    companion object Companion {
        const val SCREENY_DATABASE = "screeny.db"
    }
}

@Suppress("KotlinNoActualForExpect")
expect object AppDatabaseConstructor : RoomDatabaseConstructor<ScreenyDatabase> {
    override fun initialize(): ScreenyDatabase
}


fun getRoomDatabase(
    builder: RoomDatabase.Builder<ScreenyDatabase>
): ScreenyDatabase {
    return builder
        .setDriver(BundledSQLiteDriver())
        .setQueryCoroutineContext(Dispatchers.IO)
        .fallbackToDestructiveMigration(true)
        .build()
}