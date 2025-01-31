package com.shahid.iqbal.screeny.data.local.converter

import androidx.room.TypeConverter
import com.shahid.iqbal.screeny.ui.screens.settings.utils.AppMode

class AppModeConverter {

    @TypeConverter
    fun fromAppMode(appMode: AppMode): String {
        return appMode.name
    }

    @TypeConverter
    fun toAppMode(name: String): AppMode {
        return try {
            AppMode.valueOf(name)
        } catch (e: IllegalArgumentException) {
            AppMode.DEFAULT
        }
    }

}