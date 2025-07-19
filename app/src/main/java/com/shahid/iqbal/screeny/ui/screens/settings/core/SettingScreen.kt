package com.shahid.iqbal.screeny.ui.screens.settings.core

import android.os.Build
import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.LocalOverscrollConfiguration
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.shahid.iqbal.screeny.R
import com.shahid.iqbal.screeny.ui.routs.Routs
import com.shahid.iqbal.screeny.ui.screens.settings.components.AppModeDialog
import com.shahid.iqbal.screeny.ui.screens.settings.components.DynamicColorDialog
import com.shahid.iqbal.screeny.ui.screens.settings.components.RateUsDialog
import com.shahid.iqbal.screeny.ui.screens.settings.components.SettingsItem
import com.shahid.iqbal.screeny.ui.screens.settings.utils.AppMode
import com.shahid.iqbal.screeny.ui.screens.settings.utils.findLanguageByCode
import com.shahid.iqbal.screeny.utils.Extensions.rateUs
import org.koin.androidx.compose.koinViewModel
import java.util.Locale

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SettingsScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    settingViewModel: SettingViewModel = koinViewModel()
) {

    val userPreference by settingViewModel.userPreference.collectAsStateWithLifecycle()
    val state by settingViewModel.state.collectAsStateWithLifecycle()

    val activity = LocalActivity.current
    val context = LocalContext.current

    CompositionLocalProvider(LocalOverscrollConfiguration provides null) {

        Column(
            modifier = modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = stringResource(R.string.general),
                maxLines = 1,
                fontSize = 16.sp,
                style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold),
                modifier = Modifier
                    .fillMaxWidth(0.95f)
                    .wrapContentHeight(),
                textAlign = TextAlign.Start
            )

            Spacer(Modifier.height(10.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth(0.95f)
                    .background(
                        color = MaterialTheme.colorScheme
                            .surfaceContainer, shape = RoundedCornerShape(16.dp)
                    )
                    .border(
                        color = MaterialTheme.colorScheme
                            .outline,
                        width = 1.dp, shape = RoundedCornerShape(16.dp)
                    )
                    .padding(horizontal = 10.dp, vertical = 5.dp)
            ) {

                SettingsItem(
                    title = R.string.app_lanuage,
                    description = if (Locale.getDefault().language.contains(userPreference.languageCode)) stringResource(
                        R.string.system_default
                    ) else findLanguageByCode(userPreference.languageCode).languageName,
                    icon = R.drawable.language_icon,
                    onClick = { navController.navigate(Routs.Language) })

                HorizontalDivider(modifier = Modifier.padding(horizontal = 10.dp))


                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                    SettingsItem(
                        title = R.string.dynamic_color,
                        description =
                            if (userPreference.shouldShowDynamicColor) stringResource(R.string.on).uppercase()
                            else stringResource(R.string.off).uppercase(),
                        icon = R.drawable.dynamic_color,
                        onClick = {
                            settingViewModel.onEvent(SettingEvent.ToggleDynamicDialog)
                        }
                    )

                    HorizontalDivider(modifier = Modifier.padding(horizontal = 10.dp))
                }


                SettingsItem(
                    title = R.string.app_mode,
                    description = when (userPreference.appMode) {
                        AppMode.LIGHT -> stringResource(R.string.light)
                        AppMode.DARK -> stringResource(R.string.dark)
                        AppMode.DEFAULT -> stringResource(R.string.system_default)
                    },
                    icon = R.drawable.app_mode,
                    onClick = {
                        settingViewModel.onEvent(SettingEvent.ToggleAppModeDialog)
                    }
                )
            }

            Spacer(Modifier.height(20.dp))

            Text(
                text = stringResource(R.string.others),
                maxLines = 1,
                fontSize = 16.sp,
                style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold),
                modifier = Modifier
                    .fillMaxWidth(0.95f)
                    .wrapContentHeight(),
                textAlign = TextAlign.Start
            )

            Spacer(Modifier.height(10.dp))
            Column(
                modifier = Modifier
                    .fillMaxWidth(0.95f)
                    .background(
                        color = MaterialTheme.colorScheme
                            .surfaceContainer, shape = RoundedCornerShape(16.dp)
                    )
                    .border(
                        color = MaterialTheme.colorScheme
                            .outline,
                        width = 1.dp, shape = RoundedCornerShape(16.dp)
                    )
                    .padding(horizontal = 10.dp, vertical = 5.dp)
            ) {
                SettingsItem(
                    title = R.string.share_app,
                    description = null,
                    icon = R.drawable.share_app_icon,
                    onClick = {})

                HorizontalDivider(modifier = Modifier.padding(horizontal = 10.dp))

                SettingsItem(
                    title = R.string.rate_us,
                    description = null,
                    icon = R.drawable.rate_us_icon,
                    onClick = {
                        settingViewModel.onEvent(SettingEvent.ToggleRateUsDialog)
                    })
                HorizontalDivider(modifier = Modifier.padding(horizontal = 10.dp))
                SettingsItem(
                    title = R.string.feedback,
                    description = null,
                    icon = R.drawable.feeback_icon,
                    onClick = {})
                HorizontalDivider(modifier = Modifier.padding(horizontal = 10.dp))
                SettingsItem(
                    title = R.string.privacy_policy,
                    description = null,
                    icon = R.drawable.privacy_policy_icon,
                    onClick = {})
            }
        }
    }

    if (state.showRateUsDialog) {
        RateUsDialog(
            modifier = Modifier,
            onDismiss = {
                settingViewModel.onEvent(SettingEvent.ToggleRateUsDialog)
            },
            onRating = { userRating ->
                settingViewModel.onEvent(SettingEvent.ToggleRateUsDialog)
                context.rateUs(activity)
            }
        )
    }

    if (state.showDynamicDialog) {
        DynamicColorDialog(
            modifier = Modifier,
            shouldShowDynamicColor = userPreference.shouldShowDynamicColor,
            onDismissRequest = {
                settingViewModel.onEvent(SettingEvent.ToggleDynamicDialog)
            },
            onEnabled = {
                with(settingViewModel) {
                    onEvent(SettingEvent.UpdateDynamicColor(true))
                    onEvent(SettingEvent.ToggleDynamicDialog)
                }
            },
            onDisabled = {
                with(settingViewModel) {
                    onEvent(SettingEvent.UpdateDynamicColor(false))
                    onEvent(SettingEvent.ToggleDynamicDialog)
                }
            }
        )
    }

    if (state.showAppModeDialog) {
        AppModeDialog(
            modifier = Modifier,
            appMode = userPreference.appMode,
            onDismissRequest = {
                settingViewModel.onEvent(SettingEvent.ToggleAppModeDialog)
            },
            onSelect = {
                settingViewModel.onEvent(SettingEvent.UpdateAppMode(it))
                settingViewModel.onEvent(SettingEvent.ToggleAppModeDialog)
            }
        )
    }

}

