package com.heb.soli.media

import com.heb.soli.api.Media
import com.heb.soli.api.MediaId
import com.heb.soli.api.NetworkClient
import com.heb.soli.api.PodcastFeed
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class MediaRepository(private val networkClient: NetworkClient) {

    private val mediaList: MutableList<Media> = mutableListOf()
    private val podcastFeedList = listOf(
        "https://www.afterhate.fr/feed/podcast",
        "https://feeds.soundcloud.com/users/soundcloud:users:274829367/sounds.rss",
        "https://feeds.audiomeans.fr/feed/8ca7aac1-479f-471c-b3a4-8b487e552bff.xml"
    )

    fun getMedia(id: MediaId) =
        mediaList.firstOrNull { it.id == id }

    fun getRadioList(): Flow<List<Media>> = flow {
        val medias = networkClient.fetchAllRadios().map {
            mediaList.add(it)
            it
        }.toList()
        emit(medias)
    }

    fun getPodcasts(): Flow<List<PodcastFeed>> = flow {
        val feeds = podcastFeedList.map {
            networkClient.fetchPodcastFeed(it)
        }.toList()
        emit(feeds)
    }
}
