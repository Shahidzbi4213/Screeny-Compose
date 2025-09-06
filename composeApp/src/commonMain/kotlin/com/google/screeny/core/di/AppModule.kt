package com.google.screeny.core.di

import com.google.screeny.ui.screens.splash.SplashViewModel
import org.koin.android.annotation.KoinViewModel
import org.koin.core.annotation.Module


@Module
class AppModule {

    @KoinViewModel
    fun provideSplashViewModel(): SplashViewModel {
        return SplashViewModel()
    }
}