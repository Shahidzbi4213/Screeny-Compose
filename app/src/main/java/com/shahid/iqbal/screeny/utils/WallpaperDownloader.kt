package com.shahid.iqbal.screeny.utils

import android.app.DownloadManager
import android.content.Context
import android.os.Build
import android.os.Environment
import androidx.core.net.toUri


interface Downloader {
    fun downloadFile(url: String)
}

class WallpaperDownloader(private val context: Context) : Downloader {

    private val downloadManager by lazy { context.getSystemService(DownloadManager::class.java) }

    override fun downloadFile(url: String) {
        val request = DownloadManager.Request(url.toUri())
            .setMimeType("image/png")
            .setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE or DownloadManager.Request.NETWORK_WIFI)
            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            .also {
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q)
                    it.setVisibleInDownloadsUi(true)
            }
            .setDestinationInExternalPublicDir(
                Environment.DIRECTORY_PICTURES,
                "Screeny/${System.currentTimeMillis()}.png"
            )
            .setAllowedOverMetered(true)

        downloadManager.enqueue(request)
    }

}
