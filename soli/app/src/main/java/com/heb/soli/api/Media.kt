package com.heb.soli.api

import kotlinx.serialization.Serializable

val NO_MEDIA_ID = MediaId(-1)
val NO_MEDIA = Media(id = NO_MEDIA_ID, name = "no_media", url = "", MediaType.NO_MEDIA)

@Serializable
data class Media(val id: MediaId, val name: String, val url: String, val type: MediaType)

@Serializable
enum class MediaType {
    RADIO_STREAM,
    PODCAST_EPISODE,
    TRACK,
    NO_MEDIA
}

@Serializable
data class MediaId(val id: Int)

data class RadioStream(val name: String, val uri: String)

data class PodcastEpisode(
    val title: String,
    val uri: String,
    val imageUrl: String
)

data class PodcastFeed(val name: String, val imageUrl: String, val episodes: List<PodcastEpisode>)

fun PodcastEpisode.toMedia() : Media {
    return Media(id = MediaId(0), name = title, url = uri, MediaType.PODCAST_EPISODE)
}
