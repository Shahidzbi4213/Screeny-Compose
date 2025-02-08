package com.shahid.iqbal.screeny.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import com.shahid.iqbal.screeny.R

val screenyFontFamily = FontFamily(Font(R.font.poppins_regular))


// Default Material 3 typography values
val baseline = Typography()

val AppTypography = Typography(
    displayLarge = baseline.displayLarge.copy(fontFamily = screenyFontFamily),
    displayMedium = baseline.displayMedium.copy(fontFamily = screenyFontFamily),
    displaySmall = baseline.displaySmall.copy(fontFamily = screenyFontFamily),
    headlineLarge = baseline.headlineLarge.copy(fontFamily = screenyFontFamily),
    headlineMedium = baseline.headlineMedium.copy(fontFamily = screenyFontFamily),
    headlineSmall = baseline.headlineSmall.copy(fontFamily = screenyFontFamily),
    titleLarge = baseline.titleLarge.copy(fontFamily = screenyFontFamily),
    titleMedium = baseline.titleMedium.copy(fontFamily = screenyFontFamily),
    titleSmall = baseline.titleSmall.copy(fontFamily = screenyFontFamily),
    bodyLarge = baseline.bodyLarge.copy(fontFamily = screenyFontFamily),
    bodyMedium = baseline.bodyMedium.copy(fontFamily = screenyFontFamily),
    bodySmall = baseline.bodySmall.copy(fontFamily = screenyFontFamily),
    labelLarge = baseline.labelLarge.copy(fontFamily = screenyFontFamily),
    labelMedium = baseline.labelMedium.copy(fontFamily = screenyFontFamily),
    labelSmall = baseline.labelSmall.copy(fontFamily = screenyFontFamily),
)