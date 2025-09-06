package com.google.screeny.data.local

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase


fun getDatabaseBuilder(context: Context): RoomDatabase.Builder<ScreenyDatabase> {
    val appContext = context.applicationContext
    val dbFile = appContext.getDatabasePath(ScreenyDatabase.SCREENY_DATABASE)
    return Room.databaseBuilder<ScreenyDatabase>(
        context = appContext,
        name = dbFile.absolutePath
    )
}