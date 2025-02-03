package com.shahid.iqbal.screeny.ui.screens.settings.utils

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable

@androidx.annotation.Keep
@Stable
data class LanguageEntity(
    val countryCode: String,
    val languageName: String,
    val languageCode: String = "en",
    val flag: String
)