package com.heb.soli.media

import com.heb.soli.api.Media
import com.heb.soli.api.MediaId
import com.heb.soli.api.MediaType
import com.heb.soli.api.NetworkClient

class MediaRepository(private val networkClient: NetworkClient) {

    private val mediaList: MutableList<Media> = mutableListOf()

    suspend fun fetchRadioList() {
        val radioList = networkClient.fetchAllRadios()
        mediaList.clear()
        mediaList.addAll(radioList)
    }

    fun getMedia(id: MediaId) = mediaList.firstOrNull { it.id == id }

    fun getRadioList() = mediaList.filter { it.type == MediaType.RADIO_STREAM }.toList()
}
