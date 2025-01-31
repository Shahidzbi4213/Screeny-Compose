package com.shahid.iqbal.screeny.ui.screens.settings.utils

import android.app.LocaleManager
import android.content.Context
import android.os.Build
import android.os.LocaleList
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.Stable
import androidx.core.os.LocaleListCompat
import com.shahid.iqbal.screeny.utils.Extensions.debug
import java.util.Locale


@ReadOnlyComposable
@Composable
fun currentAppMode(appMode: AppMode): Boolean = when (appMode) {
    AppMode.DEFAULT -> isSystemInDarkTheme()
    AppMode.LIGHT -> false
    AppMode.DARK -> true
}


fun findLanguageByCode(code: String): LanguageEntity {
    return LANGUAGES_LIST.find { it.languageCode == code } ?: LANGUAGES_LIST[0]
}

fun setUserSelectedLanguageForApp(context: Context, languageCode: String) {


    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        context.getSystemService(LocaleManager::class.java).applicationLocales =
            LocaleList.forLanguageTags(languageCode)
    } else {


        AppCompatDelegate.setApplicationLocales(
            LocaleListCompat.forLanguageTags(languageCode)
        )
    }
}