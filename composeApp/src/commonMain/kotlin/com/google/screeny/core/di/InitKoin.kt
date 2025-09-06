package com.google.screeny.core.di

import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration
import org.koin.ksp.generated.module


fun initKoin(config: KoinAppDeclaration? = null){

    startKoin {
        config?.invoke(this)
        modules(NetworkModule().module)
    }
}