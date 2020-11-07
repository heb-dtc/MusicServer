package net.hebus.server.http

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
