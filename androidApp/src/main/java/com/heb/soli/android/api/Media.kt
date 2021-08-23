package com.heb.soli.android.api

import kotlinx.serialization.Serializable
import java.time.Duration

val NO_MEDIA_ID = MediaId("NO_MEDIA_ID")
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
data class MediaId(val id: String)

@Serializable
data class RadioStream(val id: MediaId, val name: String, val uri: String)

data class PodcastEpisode(
    val id: MediaId,
    val playUri: String,
    val title: String,
    val subtitle: String? = null,
    val summary: String? = null,
    val uri: String,
    val imageUrl: String,
    val duration: Duration? = null
)

data class PodcastFeed(val id: String, val name: String, val imageUrl: String, val episodes: List<PodcastEpisode>)

fun RadioStream.toMedia() : Media {
    return Media(id = id, name = name, url = uri, MediaType.RADIO_STREAM)
}

fun PodcastEpisode.toMedia() : Media {
    return Media(id = id, name = title, url = playUri, MediaType.PODCAST_EPISODE)
}
