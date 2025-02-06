package com.shahid.iqbal.screeny.di

import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.shahid.iqbal.screeny.data.local.database.PexelWallpaperDatabase
import com.shahid.iqbal.screeny.data.utils.Constant
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module


val wallpaperDatabaseModule = module {

    single {
        Room.databaseBuilder(
            get(), PexelWallpaperDatabase::class.java,
            Constant.PEXEL_WALLPAPER_DATABASE
        )
            .fallbackToDestructiveMigration()
            .fallbackToDestructiveMigrationOnDowngrade()
            .addCallback(object : RoomDatabase.Callback() {
                override fun onCreate(db: SupportSQLiteDatabase) {
                    CoroutineScope(Dispatchers.IO).launch {

                        val cursor = db.query("SELECT * FROM user_preference")
                        if (!cursor.moveToNext()) {
                            db.execSQL("INSERT INTO user_preference (id, languageCode, appMode, shouldShowDynamicColor) VALUES (1, 'en', 0, 1)")
                        }
                    }
                }
            })
            .build()
    }

    singleOf(PexelWallpaperDatabase::pexelWallpaperDao)
    singleOf(PexelWallpaperDatabase::pexelWallpaperRemoteKeysDao)
    singleOf(PexelWallpaperDatabase::recentSearchDao)
    singleOf(PexelWallpaperDatabase::favouriteWallpaperDao)
    singleOf(PexelWallpaperDatabase::userPreferenceDao)
}