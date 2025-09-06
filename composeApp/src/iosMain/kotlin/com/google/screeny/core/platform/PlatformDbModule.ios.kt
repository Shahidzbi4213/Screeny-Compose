package com.google.screeny.core.platform

import com.google.screeny.data.local.ScreenyDatabase
import com.google.screeny.data.local.getDatabaseBuilder
import com.google.screeny.data.local.getRoomDatabase
import org.koin.dsl.module

actual fun platformDbModule(): org.koin.core.module.Module {
    return module {
        single<ScreenyDatabase> {
            getRoomDatabase(
                getDatabaseBuilder()
            )
        }
    }
}


