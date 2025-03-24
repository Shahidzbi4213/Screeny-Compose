package com.shahid.iqbal.screeny.ui.screens.settings.core

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shahid.iqbal.screeny.data.repositories.UserPreferenceRepo
import com.shahid.iqbal.screeny.models.UserPreference
import com.shahid.iqbal.screeny.ui.screens.settings.utils.AppMode
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


data class SettingScreenState(
    var showDynamicDialog: Boolean = false,
    var showAppModeDialog: Boolean = false,
    var showRateUsDialog: Boolean = false
)

sealed interface SettingEvent {
    data object ToggleDynamicDialog : SettingEvent
    data object ToggleAppModeDialog : SettingEvent
    data class UpdateDynamicColor(val isDynamicColor: Boolean) : SettingEvent
    data class UpdateAppMode(val appMode: AppMode) : SettingEvent
    data object ToggleRateUsDialog : SettingEvent
}

class SettingViewModel(private val preferenceRepo: UserPreferenceRepo) : ViewModel() {

    val userPreference = preferenceRepo.uerPreference.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = UserPreference()
    )

    private val _state = MutableStateFlow(SettingScreenState())
    var state = _state.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), SettingScreenState())


    fun onEvent(event: SettingEvent) {
        when (event) {
            SettingEvent.ToggleAppModeDialog -> {
                _state.update { it.copy(showAppModeDialog = !it.showAppModeDialog) }
            }

            SettingEvent.ToggleDynamicDialog -> {
                _state.update { it.copy(showDynamicDialog = !it.showDynamicDialog) }
            }

            SettingEvent.ToggleRateUsDialog -> {
                _state.update { it.copy(showRateUsDialog = !it.showRateUsDialog) }
            }

            is SettingEvent.UpdateDynamicColor -> {
                viewModelScope.launch {
                    preferenceRepo.updateDynamicColor(event.isDynamicColor)
                }
            }

            is SettingEvent.UpdateAppMode -> {
                viewModelScope.launch {
                    preferenceRepo.updateAppMode(event.appMode)
                }
            }


        }
    }


}