package com.shahid.iqbal.screeny.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.shahid.iqbal.screeny.data.utils.Constant.PEXEL_WALLPAPER_REMOTE_KEYS_TABLE


@Entity(PEXEL_WALLPAPER_REMOTE_KEYS_TABLE)
data class WallpaperRemoteKeys(
    @PrimaryKey(autoGenerate = false) val id: Int, val prevPage: Int?, val nextPage: Int?
)