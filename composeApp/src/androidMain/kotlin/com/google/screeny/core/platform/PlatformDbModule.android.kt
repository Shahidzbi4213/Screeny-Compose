package com.google.screeny.core.platform

import android.content.Context
import com.google.screeny.data.local.ScreenyDatabase
import com.google.screeny.data.local.getDatabaseBuilder
import com.google.screeny.data.local.getRoomDatabase
import org.koin.core.annotation.Module
import org.koin.core.annotation.Single
import org.koin.ksp.generated.module

actual fun platformDbModule(): org.koin.core.module.Module
{
   return AndroidDbModule().module
}


@Module
class AndroidDbModule(){

    @Single
    fun provideDb(context: Context): ScreenyDatabase{
        val builder = getDatabaseBuilder(context)
        return getRoomDatabase(builder)
    }
}