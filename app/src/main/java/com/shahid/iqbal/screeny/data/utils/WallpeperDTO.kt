package com.shahid.iqbal.screeny.data.utils

import com.shahid.iqbal.screeny.models.CommonWallpaperEntity
import com.shahid.iqbal.screeny.models.Wallpaper

fun Wallpaper.toCommonWallpaperEntity() = CommonWallpaperEntity(id = id, url = wallpaperSource.portrait)