package com.shahid.iqbal.screeny.ui.screens.settings.language

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.intl.Locale
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shahid.iqbal.screeny.data.repositories.UserPreferenceRepo
import com.shahid.iqbal.screeny.ui.screens.settings.utils.LANGUAGES_LIST
import com.shahid.iqbal.screeny.ui.screens.settings.utils.LanguageEntity
import com.shahid.iqbal.screeny.ui.screens.settings.utils.setUserSelectedLanguageForApp
import kotlinx.coroutines.NonCancellable
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LanguageViewModel(private val userPreferenceRepo: UserPreferenceRepo) : ViewModel() {

    private val defaultLanguage =
        LANGUAGES_LIST.find { it.languageCode.contains(Locale.current.language) }
            ?: LANGUAGES_LIST.first { it.languageCode.contains("en") }


    val languagesList = userPreferenceRepo.uerPreference
        .map { preference ->
            LANGUAGES_LIST.filter { it.languageCode != preference.languageCode }
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), LANGUAGES_LIST)


    val currentLanguage = userPreferenceRepo.uerPreference.map { preference ->
        LANGUAGES_LIST.first { it.languageCode == preference.languageCode }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), defaultLanguage)

    var localSelected = MutableStateFlow<LanguageEntity?>(null)
        private set


    fun updateCurrentLanguage(languageEntity: LanguageEntity) {
        viewModelScope.launch {
            userPreferenceRepo.updateAppLanguage(languageEntity)
        }
    }

    fun updateLocalLanguageSelection(languageEntity: LanguageEntity?) {
        viewModelScope.launch {
            localSelected.value = languageEntity
        }
    }
}