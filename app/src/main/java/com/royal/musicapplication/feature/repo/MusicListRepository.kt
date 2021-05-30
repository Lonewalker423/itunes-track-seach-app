package com.royal.musicapplication.feature
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.royal.musicapplication.feature.service.MusicService
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface MusicListRepository {
    fun getSearchResultStream(query: String): Flow<PagingData<Track>>

    class Network
    @Inject constructor(
        private val service: MusicService
    ) : MusicListRepository {

       
      override  fun getSearchResultStream(query: String): Flow<PagingData<Track>> {
            return Pager(
                config = PagingConfig(pageSize = NETWORK_PAGE_SIZE, enablePlaceholders = false),
                pagingSourceFactory = { MusicPagingSource(service, query) }
            ).flow
        }

    }

    companion object {
        const val NETWORK_PAGE_SIZE = 50
    }
}
