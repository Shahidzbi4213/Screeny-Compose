package com.google.screeny.data.remote

import com.google.screeny.domain.models.WallpaperResponse

interface PexelWallpapersApi {

    suspend fun getWallpapers(page: Int): WallpaperResponse

    suspend fun searchWallpaper(page: Int, query: String): WallpaperResponse
}