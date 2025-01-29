package com.shahid.iqbal.screeny.di

import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.shahid.iqbal.screeny.data.local.database.PexelWallpaperDatabase
import com.shahid.iqbal.screeny.data.utils.Constant
import com.shahid.iqbal.screeny.models.UserPreference
import com.shahid.iqbal.screeny.ui.screens.settings.utils.AppMode
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.dsl.module
import java.util.Locale


val wallpaperDatabaseModule = module {

    single {
        Room.databaseBuilder(get(), PexelWallpaperDatabase::class.java, Constant.PEXEL_WALLPAPER_DATABASE)
            .fallbackToDestructiveMigration()
            .fallbackToDestructiveMigrationOnDowngrade()
            .addCallback(object : RoomDatabase.Callback() {
                override fun onCreate(db: SupportSQLiteDatabase) {
                    CoroutineScope(Dispatchers.IO).launch {
                      db.execSQL("INSERT INTO user_preference (id, languageCode, appMode, shouldShowDynamicColor) VALUES (1, 'en', 0, 1)")
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