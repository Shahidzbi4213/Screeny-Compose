package com.google.screeny.core.platform

import android.app.Application
import com.google.screeny.core.di.initKoin
import org.koin.android.ext.koin.androidContext

class MyApp : Application() {

    override fun onCreate() {
        super.onCreate()

        initKoin {
            androidContext(this@MyApp)
        }

    }
}