package com.shahid.iqbal.screeny.ui.screens.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.shahid.iqbal.screeny.data.repositories.RecentSearchRepository
import com.shahid.iqbal.screeny.data.repositories.SearchWallpapersRepository
import com.shahid.iqbal.screeny.models.RecentSearch
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class SearchViewModel(
    private val repo: RecentSearchRepository,
    private val searchRepo: SearchWallpapersRepository
) : ViewModel() {

    val recentSearches = repo.recentSearches.stateIn(viewModelScope, SharingStarted.WhileSubscribed(3000), emptyList())


    fun searchWallpapers(query: String) = searchRepo.getSearchWallpapers(query).flow.cachedIn(viewModelScope)


    fun saveRecentSearch(query: String) {
        viewModelScope.launch {
            repo.saveRecent(RecentSearch(query))
        }
    }

    fun removeRecentSearch(query: String) {
        viewModelScope.launch {
            repo.removeRecent(RecentSearch(query))
        }
    }

    fun clearAllRecent() {
        viewModelScope.launch {
            repo.clearAllRecent()
        }
    }

}