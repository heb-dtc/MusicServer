package net.hebus.repository

import com.heb.soli.api.Media

class HistoryRepository {
    private val historyList: MutableList<Media> = mutableListOf()

    fun addPlayedMedia(media: Media) {
        historyList.add(media)
    }

    fun getPlayedHistory() = historyList
}