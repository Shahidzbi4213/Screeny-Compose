package com.shahid.iqbal.screeny.data.repositories

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.shahid.iqbal.screeny.data.local.database.PexelWallpaperDatabase
import com.shahid.iqbal.screeny.data.paging.PexelWallpaperRemoteMediator
import com.shahid.iqbal.screeny.data.remote.PexelWallpapersApi
import com.shahid.iqbal.screeny.data.utils.Constant
import com.shahid.iqbal.screeny.models.Wallpaper


@OptIn(ExperimentalPagingApi::class)
class WallpaperRepository(
    private val api: PexelWallpapersApi,
    private val database: PexelWallpaperDatabase
) {

    fun getAllWallpapers(): Pager<Int, Wallpaper> {

        val pageConfig = PagingConfig(
            pageSize = Constant.PER_PAGE_ITEMS,
            initialLoadSize = Constant.PER_PAGE_ITEMS,
            prefetchDistance = 10,
            enablePlaceholders = false
        )
        val pagingSourceFactory = { database.pexelWallpaperDao().getAllWallpapers() }
        val remoteMediator = PexelWallpaperRemoteMediator(database, api)

        return Pager(
            config = pageConfig,
            pagingSourceFactory = pagingSourceFactory,
            remoteMediator = remoteMediator
        )
    }
}