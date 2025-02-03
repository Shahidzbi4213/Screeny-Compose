package com.shahid.iqbal.screeny.ui.screens.settings.language

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.LocalOverscrollConfiguration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.shahid.iqbal.screeny.R
import com.shahid.iqbal.screeny.ui.screens.settings.components.SingleLanguageItem
import com.shahid.iqbal.screeny.ui.screens.settings.components.Toolbar
import com.shahid.iqbal.screeny.ui.screens.settings.utils.LANGUAGES_LIST
import com.shahid.iqbal.screeny.ui.screens.settings.utils.LanguageEntity
import com.shahid.iqbal.screeny.ui.screens.settings.utils.setUserSelectedLanguageForApp
import com.shahid.iqbal.screeny.ui.theme.screenyFontFamily
import org.koin.androidx.compose.koinViewModel


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun LanguageScreen(
    modifier: Modifier = Modifier, navController: NavController,
    languageViewModel: LanguageViewModel = koinViewModel()
) {

    val currentLanguage by languageViewModel.currentLanguage.collectAsStateWithLifecycle()
    val localSelected by languageViewModel.localSelected.collectAsStateWithLifecycle()
    val languagesList by languageViewModel.languagesList.collectAsStateWithLifecycle()
    val context = LocalContext.current

    Column(
        modifier = modifier
            .fillMaxSize()
            .safeDrawingPadding(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {


        Toolbar(
            onBackPress = { navController.navigateUp() },
            isApplyEnable = localSelected != null && localSelected != currentLanguage,
            onApply = {
                setUserSelectedLanguageForApp(context, localSelected!!.languageCode)
                languageViewModel.updateCurrentLanguage(localSelected!!)
                navController.navigateUp()
            }
        )


        Spacer(modifier = Modifier.height(10.dp))

        Text(
            stringResource(id = R.string.selected_language),
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            textAlign = TextAlign.Start,
        )


        SingleLanguageItem(
            modifier = Modifier
                .wrapContentHeight()
                .fillMaxWidth(0.95f),
            language = currentLanguage,
            isSelected = localSelected == null,
            canApplyBg = true,
            onClick = { languageViewModel.updateLocalLanguageSelection(null) })


        Spacer(modifier = Modifier.height(20.dp))
        Text(
            stringResource(id = R.string.all_languages),
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
            fontFamily = screenyFontFamily,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp),
            textAlign = TextAlign.Start,
        )


        Spacer(modifier = Modifier.height(10.dp))

        CompositionLocalProvider(LocalOverscrollConfiguration provides null) {

            LazyColumn(
                verticalArrangement = Arrangement.SpaceEvenly,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                items(languagesList) { language ->

                    SingleLanguageItem(
                        language = language,
                        isSelected = localSelected == language,
                        onClick = languageViewModel::updateLocalLanguageSelection,
                        modifier = Modifier.height(50.dp).fillMaxWidth(0.95f)
                    )
                }
            }
        }
    }
}






