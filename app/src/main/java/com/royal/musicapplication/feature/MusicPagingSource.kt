package com.royal.musicapplication.feature

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.paging.PagingSource
import androidx.paging.PagingState
import androidx.paging.map
import com.bumptech.glide.load.HttpException
import com.royal.musicapplication.feature.MusicListRepository.Companion.NETWORK_PAGE_SIZE
import com.royal.musicapplication.feature.service.MusicService
import java.io.IOException

private const val LIST_STARTING_PAGE_INDEX = 1

class MusicPagingSource(
    private val service: MusicService,
    private val query: String
) : PagingSource<Int, Track>() {
    @RequiresApi(Build.VERSION_CODES.N)
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Track> {
        val position = params.key ?: LIST_STARTING_PAGE_INDEX
        return try {
            val response = service.searchMusic(
                query = query,
                entity = "musicTrack",
                page = position,
                limit = NETWORK_PAGE_SIZE
            )
            val data = response.results

            //Hashmap to store list of tracks by unique artist
            val hashmap = LinkedHashMap<Int, ArrayList<Track>>()
            val sortedList = ArrayList<Track>()

            data.forEach {
                //If key exists push the track to existing ArrayList OtherWise create a new key,ArrayList pair
                if (hashmap.containsKey(it.artistId)) {
                    hashmap[it.artistId]?.add(it)
                } else {
                    var arrayList = ArrayList<Track>()
                    arrayList.add(it)
                    hashmap[it.artistId] = arrayList
                }
            }
            hashmap.keys.map { key ->
                sortedList.addAll(hashmap[key]!!)
            }
            val nextKey = if (position == 1) {
                null
            } else {
                position + (params.loadSize / NETWORK_PAGE_SIZE)
            }
            LoadResult.Page(
                data = sortedList,
                prevKey = if (position == LIST_STARTING_PAGE_INDEX) null else position - 1,
                nextKey = nextKey
            )
        } catch (exception: IOException) {
            LoadResult.Error(exception)
        } catch (exception: HttpException) {
            LoadResult.Error(exception)
        }
    }

    // The refresh key is used for the initial load of the next PagingSource, after invalidation
    override fun getRefreshKey(state: PagingState<Int, Track>): Int? {
        // We need to get the previous key (or next key if previous is null) of the page
        // that was closest to the most recently accessed index.
        // Anchor position is the most recently accessed index
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}
