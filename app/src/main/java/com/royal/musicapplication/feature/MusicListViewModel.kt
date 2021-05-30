package com.royal.musicapplication.feature

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.insertSeparators
import androidx.paging.map
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class MusicListViewModel
@Inject constructor(private val movieListRepository: MusicListRepository) : ViewModel() {
    private var currentQueryValue: String? = null
    private var currentSearchResult: Flow<PagingData<UiModel>>? = null

    // inserting separators according to artists
    fun searchMusic(queryString: String): Flow<PagingData<UiModel>> {
        val lastResult = currentSearchResult
        if (queryString == currentQueryValue && lastResult != null) {
            return lastResult
        }
        currentQueryValue = queryString
        val newResult: Flow<PagingData<UiModel>> =
            movieListRepository.getSearchResultStream(queryString)
                .map { pagingData ->
                    pagingData.map { UiModel.MusicItem(it) }
                }
                .map {
                    it.insertSeparators<UiModel.MusicItem, UiModel> { before, after ->
                        if (after == null) {
                            return@insertSeparators null
                        }
                        if (before == null) {
                            return@insertSeparators UiModel.SeparatorItem(track = after.track)
                        }
                        if (before.track.artistId != after.track.artistId) {
                            return@insertSeparators UiModel.SeparatorItem(after.track)
                        } else {
                            // no separator
                            null
                        }
                    }
                }
                .cachedIn(viewModelScope)
        currentSearchResult = newResult
        return newResult
    }
}

sealed class UiModel {
    data class MusicItem(val track: Track) : UiModel()
    data class SeparatorItem(val track: Track) : UiModel()
}