package com.heb.soli.media

import com.heb.soli.api.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class MediaRepository(private val networkClient: NetworkClient) {

    private val podcastFeedUris = listOf(
        "https://www.afterhate.fr/feed/podcast",
        "https://feeds.soundcloud.com/users/soundcloud:users:274829367/sounds.rss",
        "https://feeds.audiomeans.fr/feed/8ca7aac1-479f-471c-b3a4-8b487e552bff.xml"
    )

    // TODO: improve dat!
    // poor's man local cache
    private val radioList: MutableList<RadioStream> = mutableListOf()
    private val podcastFeedList: MutableList<PodcastFeed> = mutableListOf()

    fun getRadio(id: MediaId) =
        radioList.firstOrNull { it.id == id }

    fun getPodcastFeed(title: String) =
        podcastFeedList.firstOrNull { it.name == title}

    fun getRadioList(): Flow<List<RadioStream>> = flow {
        val medias = networkClient.fetchAllRadios().map {
            radioList.add(it)
            it
        }.toList()
        emit(medias)
    }

    fun getPodcasts(): Flow<List<PodcastFeed>> = flow {
        val feeds = podcastFeedUris.map {
            val feed = networkClient.fetchPodcastFeed(it)
            podcastFeedList.add(feed)
            feed
        }.toList()
        emit(feeds)
    }

    fun getPodcastEpisode(mediaId: MediaId): PodcastEpisode? {
        val feedId = mediaId.id.substringBefore("_")

        return podcastFeedList.firstOrNull {
            it.id == feedId
        }?.episodes?.firstOrNull {
            it.id == mediaId
        }
    }
}
