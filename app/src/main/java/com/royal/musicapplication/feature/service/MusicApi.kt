package com.royal.musicapplication.feature.service

import com.royal.musicapplication.feature.MusicListResponse
import retrofit2.http.GET
import retrofit2.http.Query

internal interface MusicApi {

    @GET("search?")
    suspend fun searchMusic(
        @Query("term") query: String,
        @Query("entity") entity: String,
        @Query("page") page: Int,
        @Query("limit") limit: Int
    ): MusicListResponse
}
