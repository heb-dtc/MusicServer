package com.heb.soli.media

import com.heb.soli.api.Media
import com.heb.soli.api.MediaId
import com.heb.soli.api.MediaType
import com.heb.soli.api.NetworkClient
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class MediaRepository(private val networkClient: NetworkClient) {

    private val mediaList: MutableList<Media> = mutableListOf()

    fun getMedia(id: MediaId) =
        mediaList.firstOrNull { it.id == id }

    fun getRadioList(): Flow<List<Media>> = flow {
        val medias = networkClient.fetchAllRadios().map {
            mediaList.add(it)
            it
        }.toList()
        emit(medias)
    }
}
