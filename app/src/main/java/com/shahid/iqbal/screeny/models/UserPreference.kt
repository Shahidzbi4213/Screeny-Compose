package com.shahid.iqbal.screeny.models

import androidx.annotation.Keep
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.shahid.iqbal.screeny.ui.screens.settings.utils.AppMode

@Keep
@Entity("user_preference")
data class UserPreference(

    @PrimaryKey(autoGenerate = false)
    var id: Int = 1,

    var languageCode: String = "en",

    @TypeConverters
    var appMode: AppMode = AppMode.DEFAULT,
    var shouldShowDynamicColor: Boolean = true
)
