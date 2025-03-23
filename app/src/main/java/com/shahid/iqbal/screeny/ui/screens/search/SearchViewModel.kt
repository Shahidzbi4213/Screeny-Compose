package com.shahid.iqbal.screeny.ui.screens.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.shahid.iqbal.screeny.data.repositories.RecentSearchRepository
import com.shahid.iqbal.screeny.data.repositories.SearchWallpapersRepository
import com.shahid.iqbal.screeny.models.RecentSearch
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


data class SearchState(
    var searchQuery: String = "",
    var isExpanded: Boolean = true
)

sealed interface SearchEvent {

    data class OnQueryChange(val query: String) : SearchEvent
    data object ClearQuery : SearchEvent
    data class OnExpandChange(val value: Boolean) : SearchEvent
    data class SaveRecentSearch(val query: String) : SearchEvent
    data object ClearAllRecent : SearchEvent
}


class SearchViewModel(
    private val repo: RecentSearchRepository,
    private val searchRepo: SearchWallpapersRepository
) : ViewModel() {

    private val _searchState = MutableStateFlow(SearchState())
    val searchState: StateFlow<SearchState> = _searchState.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), SearchState())

    val recentSearches = repo.recentSearches.stateIn(viewModelScope, SharingStarted.WhileSubscribed(3000), emptyList())

    fun searchWallpapers(query: String) = searchRepo.getSearchWallpapers(query).flow.cachedIn(viewModelScope)


    fun onEvent(event: SearchEvent) {
        when (event) {
            is SearchEvent.OnQueryChange -> {
                _searchState.update { it.copy(searchQuery = event.query) }
            }

            is SearchEvent.ClearQuery -> {
                _searchState.update { it.copy(searchQuery = "") }
            }

            is SearchEvent.OnExpandChange -> {
                _searchState.update { it.copy(isExpanded = event.value) }
            }

            is SearchEvent.SaveRecentSearch -> {
                viewModelScope.launch {
                    repo.saveRecent(RecentSearch(event.query))
                }
            }

            is SearchEvent.ClearAllRecent -> {
                viewModelScope.launch {
                    repo.clearAllRecent()
                }
            }


        }
    }


}