package com.google.screeny.core.di

import com.google.screeny.core.platform.platformDbModule
import io.kotzilla.sdk.analytics.koin.analytics
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration
import org.koin.ksp.generated.module


fun initKoin(config: KoinAppDeclaration? = null) {

    startKoin {
        config?.invoke(this)
        analytics()
        modules(
            NetworkModule().module,
            platformDbModule(),
            DatabaseModule().module,
            AppModule().module
        )
    }
}