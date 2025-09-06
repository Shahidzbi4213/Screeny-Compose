package com.google.screeny.di

import android.content.Context
import androidx.room.RoomDatabase
import com.google.screeny.data.local.ScreenyDatabase
import com.google.screeny.data.local.getDatabaseBuilder
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single

@Module
class DbModule() {


    @Single
    fun provideDatabaseBuilder(context: Context): RoomDatabase.Builder<ScreenyDatabase> =
        getDatabaseBuilder(context)

}