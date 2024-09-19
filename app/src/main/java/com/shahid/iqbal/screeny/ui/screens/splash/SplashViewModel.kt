package com.shahid.iqbal.screeny.ui.screens.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SplashViewModel : ViewModel() {

    private var _progress = MutableStateFlow(0f)
    val progress: StateFlow<Float> get() = _progress


    init {
        loadProgress()
    }

    private fun loadProgress() {
        viewModelScope.launch {
            while (progress.value < 1f) {
                _progress.value += 0.01f
                delay(50)
            }
        }
    }
}
