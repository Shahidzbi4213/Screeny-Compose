package com.google.screeny.core.platform

import android.app.Application
import com.google.screeny.core.di.initKoin
import com.google.screeny.di.DbModule
import org.koin.android.ext.koin.androidContext
import org.koin.ksp.generated.module

class MyApp : Application() {

    override fun onCreate() {
        super.onCreate()

        initKoin {
            androidContext(this@MyApp)
            modules(DbModule().module)
        }

    }
}