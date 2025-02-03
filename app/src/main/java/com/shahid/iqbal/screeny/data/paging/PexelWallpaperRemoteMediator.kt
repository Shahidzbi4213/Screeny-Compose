package com.shahid.iqbal.screeny.data.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.shahid.iqbal.screeny.data.local.database.PexelWallpaperDatabase
import com.shahid.iqbal.screeny.data.remote.PexelWallpapersApi
import com.shahid.iqbal.screeny.models.Wallpaper
import com.shahid.iqbal.screeny.models.WallpaperRemoteKeys
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.concurrent.TimeUnit

@OptIn(ExperimentalPagingApi::class)
class PexelWallpaperRemoteMediator(
    private val wallpaperDatabase: PexelWallpaperDatabase,
    private val pexelWallpapersApi: PexelWallpapersApi
) : RemoteMediator<Int, Wallpaper>() {

    private val wallpaperDao = wallpaperDatabase.pexelWallpaperDao()
    private val remoteKeysDao = wallpaperDatabase.pexelWallpaperRemoteKeysDao()



    override suspend fun load(loadType: LoadType, state: PagingState<Int, Wallpaper>): MediatorResult {

        return withContext(Dispatchers.IO) {
            try {

                val page = when (loadType) {
                    LoadType.REFRESH -> {
                        val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                        remoteKeys?.nextPage?.minus(1) ?: 1
                    }

                    LoadType.PREPEND -> {
                        val remoteKeys = getRemoteKeyForFirstItem(state)
                        val prevPage = remoteKeys?.prevPage
                        prevPage ?: return@withContext MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                    }

                    LoadType.APPEND -> {
                        val remoteKeys = getRemoteKeyForLastItem(state)
                        val nextPage = remoteKeys?.nextPage
                        nextPage ?: return@withContext MediatorResult.Success(endOfPaginationReached = remoteKeys != null)
                    }
                }

                val response = pexelWallpapersApi.getWallpapers(page = page)
                val endOfPaginationReached = response.wallpapers.isEmpty()




                wallpaperDatabase.withTransaction {
                    if (loadType == LoadType.REFRESH) {
                        wallpaperDao.deleteAllWallpapers()
                        remoteKeysDao.deleteAllRemoteKeys()
                    }

                    val prevPage = if (page > 1) page - 1 else null
                    val nextPage = if (endOfPaginationReached) null else page + 1

                    val keys = response.wallpapers.shuffled().map { wallpaper ->
                        WallpaperRemoteKeys(wallpaperId = wallpaper.id, prevPage, nextPage, page)
                    }
                    remoteKeysDao.addAllRemoteKeys(remoteKeys = keys)
                    wallpaperDao.addWallpapers(response.wallpapers
                        .onEach { wallpaper -> wallpaper.page = page }
                    )
                }
                MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
            } catch (e: Exception) {
                MediatorResult.Error(e)
            }
        }
    }


    override suspend fun initialize(): InitializeAction {
        val cacheTimeout = TimeUnit.MILLISECONDS.convert(1, TimeUnit.HOURS)

        return if (System.currentTimeMillis() - (remoteKeysDao.getCreationTime() ?: 0) < cacheTimeout) {
            InitializeAction.SKIP_INITIAL_REFRESH
        } else {
            InitializeAction.LAUNCH_INITIAL_REFRESH
        }
    }


    /**
     * Retrieves the remote key for the item closest to the user's current scroll position.
     *
     * @param state The current state of the paging system, which includes the position of items loaded.
     * @return The corresponding remote key for the item closest to the current scroll position, or null if not found.
     */
    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, Wallpaper>
    ): WallpaperRemoteKeys? {

        // Get the user's current position in the list (anchorPosition).
        return withContext(Dispatchers.IO) {
            state.anchorPosition?.let { position ->

                // Find the  Wallpaper closest to that position.
                state.closestItemToPosition(position)?.id?.let { id ->

                    // Use the wallpaper's ID to retrieve the corresponding remote key from the database.
                    remoteKeysDao.getRemoteKeyByWallpaperId(id = id)
                }
            }
        }
    }

    /**
     * Retrieves the remote key for the first item in the loaded data pages.
     *
     * @param state The current state of the paging system, which includes the pages of items loaded.
     * @return The corresponding remote key for the first item in the first loaded page, or null if not found.
     */
    private suspend fun getRemoteKeyForFirstItem(
        state: PagingState<Int, Wallpaper>
    ): WallpaperRemoteKeys? {

        // Find the first page that contains data (not empty).
        return withContext(Dispatchers.IO) {
            state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()?.let { wallpaper ->

                // Use the first wallpaper's ID to retrieve the corresponding remote key from the database.
                remoteKeysDao.getRemoteKeyByWallpaperId(id = wallpaper.id)
            }
        }
    }


    /**
     * Retrieves the remote key for the last item in the loaded data pages.
     *
     * @param state The current state of the paging system, which includes the pages of items loaded.
     * @return The corresponding remote key for the last item in the last loaded page, or null if not found.
     */
    private suspend fun getRemoteKeyForLastItem(
        state: PagingState<Int, Wallpaper>
    ): WallpaperRemoteKeys? {

        // Find the last page that contains data (not empty).
        return withContext(Dispatchers.IO) {
            state.pages.lastOrNull { it.data.isNotEmpty() }?.data?.lastOrNull()?.let { wallpaper ->

                // Use the last Wallpaper's ID to retrieve the corresponding remote key from the database.
                remoteKeysDao.getRemoteKeyByWallpaperId(id = wallpaper.id)
            }
        }
    }

}