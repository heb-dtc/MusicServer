package net.hebus.repository

import net.hebus.server.http.Media

class HistoryRepository {
    private val historyList: MutableList<Media> = mutableListOf()

    fun addPlayedMedia(media: Media) {
        historyList.add(media)
    }

    fun getPlayedHistory() = historyList
}