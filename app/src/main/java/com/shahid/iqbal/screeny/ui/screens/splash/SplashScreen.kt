package com.shahid.iqbal.screeny.ui.screens.splash

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.shahid.iqbal.screeny.R
import com.shahid.iqbal.screeny.ui.screens.components.SplashProgressBar
import com.shahid.iqbal.screeny.ui.theme.screenyFontFamily
import org.koin.androidx.compose.koinViewModel

@Composable
fun SplashScreen(
    modifier: Modifier = Modifier,
    splashViewModel: SplashViewModel = koinViewModel(),
    onProgressFinish: () -> Unit
) {

    val progress by splashViewModel.progress.collectAsStateWithLifecycle()
    val context = LocalContext.current

    val splashTitle = remember {
        buildAnnotatedString {
            pushStyle(SpanStyle(fontWeight = FontWeight.Bold, fontSize = 29.sp, fontFamily = screenyFontFamily))
            append(context.getString(R.string.app_name))
            pushStyle(SpanStyle(fontWeight = FontWeight.Light, fontSize = 24.sp, fontFamily = screenyFontFamily))
            append(" Wallpaper")
        }
    }


    LaunchedEffect(key1 = progress) {
        if (progress >= 1f) {
            onProgressFinish()
        }

    }

    /*Disable Back Button on Splash Screen*/
    BackHandler(enabled = false) {}

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .consumeWindowInsets(WindowInsets(0.dp)),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {


        Image(
            painter = painterResource(id = R.drawable.app_logo),
            modifier = Modifier.size(100.dp),
            contentScale = ContentScale.Fit,
            contentDescription = stringResource(id = R.string.app_name)
        )

        Spacer(modifier = Modifier.height(10.dp))

        Text(text = splashTitle, color = MaterialTheme.colorScheme.onBackground)

        Spacer(modifier = Modifier.height(20.dp))


        SplashProgressBar(progress = progress)

    }
}