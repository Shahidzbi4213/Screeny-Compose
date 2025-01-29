package com.shahid.iqbal.screeny.ui.core

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.getValue
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.shahid.iqbal.screeny.ui.screens.settings.SettingViewModel
import com.shahid.iqbal.screeny.ui.screens.settings.utils.AppMode
import com.shahid.iqbal.screeny.ui.screens.settings.utils.currentAppMode
import com.shahid.iqbal.screeny.ui.theme.ScreenyTheme
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val settingViewModel by viewModel<SettingViewModel>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                settingViewModel.userPreference.collectLatest {
                    setEdgeToEdge(it.appMode)
                }
            }
        }

        setContent {
            val userPreference by settingViewModel.userPreference.collectAsStateWithLifecycle()
            val isDarkMode = currentAppMode(userPreference.appMode)

            ScreenyTheme(
                dynamicColor = userPreference.shouldShowDynamicColor,
                darkTheme = isDarkMode
            ) {
                ScreenyApp()

            }
        }
    }

    private fun setEdgeToEdge(appMode: AppMode) {
        val statusBarStyle: SystemBarStyle
        val navigationBarStyle: SystemBarStyle

        when (appMode) {
            AppMode.LIGHT -> {
                statusBarStyle = SystemBarStyle.dark(android.graphics.Color.TRANSPARENT)
                navigationBarStyle = SystemBarStyle.dark(android.graphics.Color.TRANSPARENT)
            }

            AppMode.DARK -> {
                statusBarStyle = SystemBarStyle.light(android.graphics.Color.TRANSPARENT,
                    android.graphics.Color.TRANSPARENT)
                navigationBarStyle = SystemBarStyle.light(android.graphics.Color.TRANSPARENT,
                    android.graphics.Color.TRANSPARENT)
            }

            AppMode.DEFAULT -> {
                enableEdgeToEdge()
                return
            }
        }

        enableEdgeToEdge(statusBarStyle, navigationBarStyle)
    }

}
