package com.shahid.iqbal.screeny.utils

import android.app.WallpaperManager
import android.content.Context
import android.graphics.drawable.Drawable
import android.widget.Toast
import androidx.core.graphics.drawable.toBitmap
import com.shahid.iqbal.screeny.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


enum class WallpaperType {
    SET_AS_HOME_SCREEN, SET_AS_LOCK_SCREEN, SET_AS_BOTH
}

class WallpaperManager(private val context: Context) {

    private val wallpaperManager by lazy {
        context.getSystemService(Context.WALLPAPER_SERVICE) as WallpaperManager
    }


    fun setWallpaper(drawable: Drawable, type: WallpaperType) {
        CoroutineScope(Dispatchers.IO).launch {
            try {

                val flag = when (type) {
                    WallpaperType.SET_AS_HOME_SCREEN -> WallpaperManager.FLAG_SYSTEM
                    WallpaperType.SET_AS_LOCK_SCREEN -> WallpaperManager.FLAG_LOCK
                    WallpaperType.SET_AS_BOTH -> WallpaperManager.FLAG_LOCK or WallpaperManager.FLAG_SYSTEM
                }

                wallpaperManager.setBitmap(drawable.toBitmap(), null, false, flag)

                withContext(Dispatchers.Main) {
                    Toast.makeText(context, context.getString(R.string.wallpaper_set_successfully), Toast.LENGTH_SHORT).show();
                }

            } catch (e: Exception) {

                withContext(Dispatchers.Main) {
                    Toast.makeText(context, context.getString(R.string.something_went_wrong), Toast.LENGTH_SHORT).show()
                }
            }


        }
    }

}