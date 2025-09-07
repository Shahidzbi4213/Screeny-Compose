package com.google.screeny.core.platform

import androidx.room.RoomDatabase
import com.google.screeny.data.local.ScreenyDatabase
import com.google.screeny.data.local.getDatabaseBuilder
import org.koin.dsl.module
import org.koin.core.module.Module


actual fun platformDbModule(): Module {
    return module { single<RoomDatabase.Builder<ScreenyDatabase>> {getDatabaseBuilder(get())} }
}