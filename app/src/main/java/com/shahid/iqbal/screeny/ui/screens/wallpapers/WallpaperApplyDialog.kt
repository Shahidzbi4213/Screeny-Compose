package com.shahid.iqbal.screeny.ui.screens.wallpapers

import android.graphics.drawable.Drawable
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.shahid.iqbal.screeny.R
import com.shahid.iqbal.screeny.ui.utils.ComponentHelpers.noRippleClickable
import com.shahid.iqbal.screeny.ui.utils.NoRippleInteractionSource
import com.shahid.iqbal.screeny.utils.WallpaperManager
import com.shahid.iqbal.screeny.utils.WallpaperType
import org.koin.compose.koinInject

private const val LAST_INDEX = 2


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WallpaperApplyDialog(wallpaper: Drawable?, onDismissRequest: () -> Unit) {

    ModalBottomSheet(
        onDismissRequest = onDismissRequest,
        modifier = Modifier

    ) {

        DialogContent(wallpaper, onDismissRequest)
    }
}


@Composable
private fun DialogContent(wallpaper: Drawable?, onCancel: () -> Unit) {

    val wallpaperManager = koinInject<WallpaperManager>()

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .wrapContentSize()
            .padding(bottom = 30.dp)
    ) {
        Text(
            text = stringResource(id = R.string.wallpaper_dialog_title),
            style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.W500),
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(top = 20.dp, start = 10.dp, end = 10.dp)
                .border(
                    shape = RoundedCornerShape(10.dp),
                    border = BorderStroke(1.dp, DividerDefaults.color)
                ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {


            WallpaperActionItem.WALLPAPER_ACTION_ITEMS.forEachIndexed { index, item ->
                SingleActionItem(wallpaperActionItem = item, index = index) {

                    wallpaper?.let {
                        when (index) {
                            0 -> wallpaperManager.setWallpaper(drawable = wallpaper, type = WallpaperType.SET_AS_HOME_SCREEN)
                            1 -> wallpaperManager.setWallpaper(drawable = wallpaper, type = WallpaperType.SET_AS_LOCK_SCREEN)
                            2 -> wallpaperManager.setWallpaper(drawable = wallpaper, type = WallpaperType.SET_AS_BOTH)
                        }

                        onCancel()
                    }
                }
            }

        }

        Button(
            onClick = onCancel, modifier = Modifier
                .fillMaxWidth(0.7f)
                .padding(top = 10.dp, bottom = 5.dp),
            interactionSource = NoRippleInteractionSource()
        ) {
            Text(text = stringResource(id = R.string.cancel), modifier = Modifier.background(Color.Transparent))
        }


    }


}

@Composable
private fun SingleActionItem(
    modifier: Modifier = Modifier,
    wallpaperActionItem: WallpaperActionItem,
    index: Int,
    onClick: () -> Unit
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(15.dp)
            .noRippleClickable { onClick() },
        verticalAlignment = Alignment.CenterVertically,
    ) {

        Image(
            painter = painterResource(id = wallpaperActionItem.icon),
            contentDescription = null,
            modifier = Modifier
                .size(35.dp)
                .padding(end = if (index == LAST_INDEX) 6.dp else 0.dp),
            colorFilter = ColorFilter.tint(color = MaterialTheme.colorScheme.onSecondaryContainer)
        )

        Text(
            text = stringResource(id = wallpaperActionItem.title),
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.W300),
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 10.dp)
        )
    }

    if (index != LAST_INDEX)
        HorizontalDivider()
}