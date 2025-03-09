package com.shahid.iqbal.screeny.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.shahid.iqbal.screeny.data.remote.PexelWallpapersApi
import com.shahid.iqbal.screeny.models.Wallpaper


class SearchWallpapersPagingSource(private val api: PexelWallpapersApi, private val query: String) : PagingSource<Int, Wallpaper>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Wallpaper> {
        try {

            if (query.isEmpty()) {
                return LoadResult.Error(Throwable("Query is Empty"))
            }

            val position = params.key ?: 1
            val response = api.searchWallpaper(position, query)
            return LoadResult.Page(
                data = response.wallpapers.sortedBy { it.id },
                prevKey = if (response.prevPage == null) null else position - 1,
                nextKey = if (response.nextPage == null) null else position + 1
            )
        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Wallpaper>): Int? {
        return state.anchorPosition?.let {
            state.closestPageToPosition(it)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(it)?.nextKey?.minus(1)
        }
    }


}