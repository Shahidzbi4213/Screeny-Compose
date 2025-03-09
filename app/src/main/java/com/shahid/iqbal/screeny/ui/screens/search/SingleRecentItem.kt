package com.shahid.iqbal.screeny.ui.screens.search

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.shahid.iqbal.screeny.R
import com.shahid.iqbal.screeny.models.RecentSearch
import com.shahid.iqbal.screeny.ui.theme.screenyFontFamily
import com.shahid.iqbal.screeny.ui.utils.ComponentHelpers.noRippleClickable

@Composable
fun SingleRecentItem(
    modifier: Modifier = Modifier,
    recentSearch: RecentSearch,
    @DrawableRes icon: Int = R.drawable.history,
    onClick: () -> Unit
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = icon),
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary
        )
        Text(
            text = recentSearch.query,
            fontFamily = screenyFontFamily,
            style = MaterialTheme.typography.titleMedium
                .copy(color = MaterialTheme.colorScheme.onSurfaceVariant),
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 10.dp)
                .noRippleClickable { onClick() })
    }
}