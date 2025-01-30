package com.shahid.iqbal.screeny.ui.screens.settings.components;

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.shahid.iqbal.screeny.R
import com.shahid.iqbal.screeny.models.UserPreference
import com.shahid.iqbal.screeny.ui.screens.components.DialogActionButtons

/**
 * Created by Shahid Iqbal on 29/1/25.
 */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DynamicColorDialog(
    modifier: Modifier = Modifier, userPreference: UserPreference, onDismissRequest: () -> Unit, onEnabled: () -> Unit, onDisabled: () -> Unit
) {


    ModalBottomSheet(
        onDismissRequest = onDismissRequest, modifier = modifier
            .wrapContentSize()
            .safeDrawingPadding()
    ) {

        DialogContent(userPreference, onSelect = { isEnabled ->
            if (isEnabled)
                onEnabled()
            else
                onDisabled()

        }, onDismissRequest)

    }
}


@Composable
private fun DialogContent(userPreference: UserPreference, onSelect: (Boolean) -> Unit, onCancel: () -> Unit) {

    var isEnabled by remember {
        mutableStateOf(userPreference.shouldShowDynamicColor)
    }

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.wrapContentSize()
    ) {

        Text(
            text = stringResource(R.string.personalize_your_experience),
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.SemiBold),
        )
        Spacer(modifier = Modifier.height(3.dp))
        Text(
            text = stringResource(R.string.match_colors_to_your_wallpaper),
            style = MaterialTheme.typography.labelSmall.copy(color = MaterialTheme.colorScheme.onSurfaceVariant)
        )

        Spacer(modifier = Modifier.height(20.dp))
        DialogComponentItem(text = stringResource(R.string.on), isSelected = isEnabled, onClick = { isEnabled = true })
        Spacer(modifier = Modifier.height(10.dp))
        DialogComponentItem(text = stringResource(R.string.off), isSelected = !isEnabled, onClick = { isEnabled = false })

        Spacer(modifier = Modifier.height(20.dp))
        DialogActionButtons(
            modifier = Modifier.fillMaxWidth(),
            successButtonText = stringResource(R.string.ok),
            cancelButtonText = stringResource(R.string.cancel),
            onSuccess = { onSelect(isEnabled) },
            onCancel = onCancel
        )

        Spacer(modifier = Modifier.height(20.dp))
    }

}


