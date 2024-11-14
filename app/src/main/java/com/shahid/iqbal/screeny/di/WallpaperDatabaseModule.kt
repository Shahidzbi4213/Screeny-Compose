package com.shahid.iqbal.screeny.di

import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.shahid.iqbal.screeny.data.local.database.PexelWallpaperDatabase
import com.shahid.iqbal.screeny.data.utils.Constant
import com.shahid.iqbal.screeny.models.UserPreference
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.dsl.module


val wallpaperDatabaseModule = module {

    single {
        Room.databaseBuilder(get(), PexelWallpaperDatabase::class.java, Constant.PEXEL_WALLPAPER_DATABASE)
            .fallbackToDestructiveMigration()
            .fallbackToDestructiveMigrationOnDowngrade()
            .addCallback(object : RoomDatabase.Callback() {
                override fun onCreate(db: SupportSQLiteDatabase) {
                    CoroutineScope(Dispatchers.IO).launch {
                        addDefaultUserPreference()
                    }
                }

                private suspend fun addDefaultUserPreference() {
                    withContext(Dispatchers.IO) {
                        val userPreference = UserPreference(
                            isDarkMode = false,
                            isDynamicColor = true,
                            languageCode = "en"
                        )

                        get<PexelWallpaperDatabase>()
                            .userPreferenceDao()
                            .addUserPreference(userPreference)
                    }
                }
            })
            .build()
    }

    single { get<PexelWallpaperDatabase>().pexelWallpaperDao() }
    single { get<PexelWallpaperDatabase>().pexelWallpaperRemoteKeysDao() }
    single { get<PexelWallpaperDatabase>().recentSearchDao() }
    single { get<PexelWallpaperDatabase>().favouriteWallpaperDao() }
    single { get<PexelWallpaperDatabase>().userPreferenceDao() }
}