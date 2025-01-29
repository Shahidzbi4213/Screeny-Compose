package com.shahid.iqbal.screeny.ui.screens.settings.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.shahid.iqbal.screeny.ui.screens.settings.utils.LanguageEntity
import com.shahid.iqbal.screeny.ui.utils.ComponentHelpers.noRippleClickable
import com.shahid.iqbal.screeny.ui.utils.NoRippleInteractionSource

@Composable
fun SingleLanguageItem(
    modifier: Modifier = Modifier,
    language: LanguageEntity,
    isSelected: Boolean,
    canApplyBg: Boolean = true,
    onClick: (LanguageEntity) -> Unit
) {

    val backgroundColor by animateColorAsState(
        targetValue = if (isSelected && canApplyBg)
            MaterialTheme.colorScheme.primaryContainer else Color.Transparent, label = "Select Language Background Color"
    )

    Row(modifier = modifier
        .fillMaxWidth()
        .wrapContentHeight()
        .clip(shape = RoundedCornerShape(10.dp))
        .noRippleClickable { onClick(language) }
        .background(
            color = backgroundColor
        )
        .padding(horizontal = 10.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center) {

        Text(
            text = language.flag, style = MaterialTheme.typography.titleMedium, modifier = Modifier
                .wrapContentSize()
                .padding(end = 10.dp)
        )

        Text(
            text = language.languageName, style = MaterialTheme.typography.titleMedium, modifier = Modifier
                .fillMaxHeight()
                .weight(1f)
        )

        RadioButton(
            selected = isSelected, onClick = null, enabled = false,
            colors = RadioButtonDefaults.colors(
                disabledUnselectedColor = RadioButtonDefaults.colors().unselectedColor,
                disabledSelectedColor = RadioButtonDefaults.colors().selectedColor,
                selectedColor = Color.White
            ),
            interactionSource = NoRippleInteractionSource()
        )
    }
}