package com.shahid.iqbal.screeny.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.shahid.iqbal.screeny.R

val screenyFontFamily = FontFamily(Font(R.font.poppins_regular))

val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = screenyFontFamily, fontWeight = FontWeight.Normal, fontSize = 16.sp, lineHeight = 24.sp, letterSpacing = 0.5.sp
    ), titleLarge = TextStyle(
        fontFamily = screenyFontFamily, fontWeight = FontWeight.Normal, fontSize = 22.sp, lineHeight = 28.sp, letterSpacing = 0.sp
    ), titleMedium = TextStyle(
        fontFamily = screenyFontFamily, fontWeight = FontWeight.Normal, fontSize = 16.sp, lineHeight = 20.sp, letterSpacing = 0.5.sp
    ), labelSmall = TextStyle(
        fontFamily = screenyFontFamily, fontWeight = FontWeight.Medium, fontSize = 11.sp, lineHeight = 16.sp, letterSpacing = 0.5.sp
    ),
    labelLarge = TextStyle(fontFamily = screenyFontFamily),
    labelMedium = TextStyle(fontFamily = screenyFontFamily),
    titleSmall = TextStyle(fontFamily = screenyFontFamily),
    headlineMedium = TextStyle(fontFamily = screenyFontFamily),
    displayLarge = TextStyle(fontFamily = screenyFontFamily),
    displaySmall = TextStyle(fontFamily = screenyFontFamily),
    bodyMedium = TextStyle(fontFamily = screenyFontFamily),
    bodySmall = TextStyle(fontFamily = screenyFontFamily),
    displayMedium = TextStyle(fontFamily = screenyFontFamily),
    headlineLarge = TextStyle(fontFamily = screenyFontFamily),
    headlineSmall = TextStyle(fontFamily = screenyFontFamily)
)