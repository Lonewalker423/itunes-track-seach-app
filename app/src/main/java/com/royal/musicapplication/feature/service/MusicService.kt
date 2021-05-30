package com.royal.musicapplication.feature.service

import retrofit2.Retrofit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MusicService
@Inject constructor(retrofit: Retrofit) : MusicApi {
    private val musicApi by lazy { retrofit.create(MusicApi::class.java) }
    override suspend fun searchMusic(
        query: String,
        entity: String,
        page: Int,
        limit: Int
    ) = musicApi.searchMusic(query, entity,  page, limit)
}
