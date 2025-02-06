package com.shahid.iqbal.screeny.ui.screens.category

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.ImageLoader
import coil.compose.AsyncImage
import coil.request.CachePolicy
import coil.request.ImageRequest
import coil.size.Size
import com.shahid.iqbal.screeny.models.Category
import com.shahid.iqbal.screeny.ui.screens.components.LoadingPlaceHolder
import com.shahid.iqbal.screeny.ui.screens.components.shimmerBrush
import com.shahid.iqbal.screeny.ui.theme.screenyFontFamily
import com.shahid.iqbal.screeny.ui.utils.ComponentHelpers.noRippleClickable
import com.shahid.iqbal.screeny.utils.Extensions.toPx
import kotlinx.coroutines.delay
import org.koin.compose.koinInject


@Composable
fun CategoryScreen(modifier: Modifier = Modifier, onCategoryClick: (String) -> Unit) {

    var showContent by remember { mutableStateOf(false) }
    val imageLoader: ImageLoader = koinInject()

    LaunchedEffect(key1 = Unit) {
        delay(250)
        showContent = true
    }


    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(16.dp),
        modifier = modifier.fillMaxSize()
    ) {


        if (showContent) {
            items(categories, key = { it.name }) { category ->
                CategoryItem(category = category, imageLoader) {
                    onCategoryClick(category.name)
                }
            }

        } else {
            items(categories.size) {
                LoadingPlaceHolder(modifier = Modifier.height(100.dp))
            }
        }

    }


}


@Composable
fun CategoryItem(category: Category, imageLoader: ImageLoader, onClick: () -> Unit) {

    val context = LocalContext.current
    val categoryImageSize = Size(800.dp.toPx().toInt(), 800.dp.toPx().toInt())
    val imageRequest = remember {
        ImageRequest.Builder(context).data(category.thumbnail)
            .diskCachePolicy(CachePolicy.ENABLED)
            .size(categoryImageSize).build()
    }

    var showShimmer by remember {
        mutableStateOf(true)
    }

    Box(
        modifier = Modifier
            .height(100.dp)
            .clip(RoundedCornerShape(10.dp))
            .noRippleClickable { onClick() },
        contentAlignment = Alignment.Center,
    ) {

        AsyncImage(
            model = imageRequest, contentDescription = category.name, imageLoader = imageLoader,
            contentScale = ContentScale.Crop, onSuccess = { showShimmer = false }, modifier = Modifier
                .matchParentSize()
                .background(
                    shimmerBrush(
                        targetValue = 1300f,
                        showShimmer = showShimmer
                    ), shape = RoundedCornerShape(10.dp)
                )

        )


        Box(
            modifier = Modifier
                .matchParentSize()
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(Color.Black, Color(0xFF29323B))
                    ), alpha = if (isSystemInDarkTheme()) 0.45f else 0.35f
                ), contentAlignment = Alignment.Center
        ) {
            Text(
                text = category.name,
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.SemiBold, fontSize = 20.sp, fontFamily = screenyFontFamily
                ),
                textAlign = TextAlign.Center,
                color = Color.White
            )
        }
    }
}