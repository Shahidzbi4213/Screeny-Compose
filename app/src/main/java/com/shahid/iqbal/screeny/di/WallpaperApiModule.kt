package com.shahid.iqbal.screeny.di

import com.shahid.iqbal.screeny.data.remote.PexelWallpapersApiImpl
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module


val wallpaperApiModule = module { singleOf(::PexelWallpapersApiImpl) }