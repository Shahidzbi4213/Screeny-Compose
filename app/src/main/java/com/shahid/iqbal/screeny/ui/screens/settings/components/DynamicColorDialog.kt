package com.shahid.iqbal.screeny.ui.screens.settings.components;

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.shahid.iqbal.screeny.R
import com.shahid.iqbal.screeny.models.UserPreference
import com.shahid.iqbal.screeny.ui.screens.settings.utils.AppMode

/**
 * Created by Shahid Iqbal on 29/1/25.
 */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DynamicColorDialog(
    modifier: Modifier = Modifier,
    userPreference: UserPreference,
    onDismissRequest: () -> Unit,
    onEnabled: () -> Unit,
    onDisabled: () -> Unit
) {


    ModalBottomSheet(
        onDismissRequest = onDismissRequest,
        modifier = modifier.safeDrawingPadding()

    ) {

        DialogContent(userPreference, onEnabled, onDisabled)

    }
}


@Composable
private fun DialogContent(userPreference: UserPreference, onEnabled: () -> Unit, onDisabled: () -> Unit) {


    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {

        DialogComponentItem(text = stringResource(R.string.on), isSelected = userPreference.shouldShowDynamicColor, onClick = onEnabled)
        Spacer(modifier = Modifier.height(10.dp))
        DialogComponentItem(text = stringResource(R.string.off), isSelected = !userPreference.shouldShowDynamicColor, onClick = onDisabled)

    }

}


