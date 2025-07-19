package com.shahid.iqbal.screeny.ui.core

import android.os.Bundle
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.shahid.iqbal.screeny.ui.screens.settings.core.SettingViewModel
import com.shahid.iqbal.screeny.ui.screens.settings.utils.currentAppMode
import com.shahid.iqbal.screeny.ui.theme.ScreenyTheme
import org.koin.androidx.compose.koinViewModel

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge(
            statusBarStyle = SystemBarStyle.auto(
                android.graphics.Color.TRANSPARENT,
                android.graphics.Color.TRANSPARENT
            ),
            navigationBarStyle = SystemBarStyle.auto(
                android.graphics.Color.TRANSPARENT,
                android.graphics.Color.TRANSPARENT
            )
        )

        setContent {
            val settingViewModel = koinViewModel<SettingViewModel>()
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


}
