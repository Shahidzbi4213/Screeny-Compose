package com.shahid.iqbal.screeny.ui.screens.settings.core

import android.app.Activity
import android.net.Uri
import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.LocalOverscrollConfiguration
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.graphics.Color.Companion.White
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
import com.shahid.iqbal.screeny.ui.screens.settings.components.RateUsDialog
import com.shahid.iqbal.screeny.ui.screens.settings.components.SettingsItem
import com.shahid.iqbal.screeny.ui.screens.settings.utils.AppMode
import com.shahid.iqbal.screeny.ui.screens.settings.utils.findLanguageByCode
import com.shahid.iqbal.screeny.utils.Extensions.debug
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
    var showRateUsDialog by remember { mutableStateOf(false) }
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
                    .background(color = White, shape = RoundedCornerShape(16.dp))
                    .border(color = Gray, width = 1.dp, shape = RoundedCornerShape(16.dp))
                    .padding(horizontal = 10.dp, vertical = 5.dp)
            ) {
                SettingsItem(title = R.string.app_lanuage,
                    description = if (Locale.getDefault().language.contains(userPreference.languageCode)) stringResource(
                        R.string.system_default
                    ) else findLanguageByCode(userPreference.languageCode).languageName,
                    icon = R.drawable.language_icon,
                    onClick = { navController.navigate(Routs.Language) })
                HorizontalDivider(modifier = Modifier.padding(horizontal = 10.dp))
                SettingsItem(title = R.string.dynamic_color,
                    description =
                    if (userPreference.shouldShowDynamicColor) stringResource(R.string.on).uppercase()
                    else stringResource(R.string.off).uppercase(),
                    icon = R.drawable.dynamic_color,
                    onClick = {})
                HorizontalDivider(modifier = Modifier.padding(horizontal = 10.dp))
                SettingsItem(title = R.string.app_mode,
                    description = when (userPreference.appMode) {
                        AppMode.LIGHT -> stringResource(R.string.light)
                        AppMode.DARK -> stringResource(R.string.dark)
                        AppMode.DEFAULT -> stringResource(R.string.system_default)
                    },
                    icon = R.drawable.app_mode, onClick = {})
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
                    .background(color = White, shape = RoundedCornerShape(16.dp))
                    .border(color = Gray, width = 1.dp, shape = RoundedCornerShape(16.dp))
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
                        showRateUsDialog = true
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

    if (showRateUsDialog) {
        RateUsDialog(modifier = Modifier,
            onDismiss = {
                showRateUsDialog = false
            },
            onRating = { userRating ->
                showRateUsDialog = false
                userRating.debug("SettingsScreen")
                context.rateUs(activity)
            }
        )
    }

}

