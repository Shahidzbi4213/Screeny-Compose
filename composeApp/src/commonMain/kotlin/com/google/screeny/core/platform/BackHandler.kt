package com.google.screeny.core.platform

import androidx.compose.runtime.Composable

@Composable
expect fun BackHandler(enable: Boolean,onBack:()-> Unit)