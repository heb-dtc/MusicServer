package com.heb.soli.api

import kotlinx.serialization.Serializable

@Serializable
data class Media(val id: MediaId, val name: String, val url: String, val type: MediaType)

@Serializable
enum class MediaType {
    RADIO_STREAM,
    PODCAST_EPISODE,
    TRACK
}

@Serializable
data class MediaId(val id: Int)

data class PodcastEpisode(
    val title: String,
    val uri: String,
    val imageUrl: String
)

data class PodcastFeed(val name: String, val imageUrl: String, val episodes: List<PodcastEpisode>)
