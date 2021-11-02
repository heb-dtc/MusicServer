package com.heb.soli.api

import io.ktor.client.*
import io.ktor.client.features.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.features.logging.*
import io.ktor.client.features.observer.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.http.content.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.encodeToJsonElement
import java.lang.Exception

const val API_ENDPOINT = "https://music.hebus.net/api"

class NetworkClient(private val httpClient: HttpClient) {

    private val TAG = NetworkClient::class.simpleName

    private val podcastFeedParser = PodcastFeedParser()

    suspend fun fetchAllRadios(): List<RadioStream> {
        return try {
            return httpClient.get("$API_ENDPOINT/radios")
        } catch (e: Exception) {
            emptyList()
        }
    }

    suspend fun fetchPodcastFeed(url: String): PodcastFeed {
        val response: HttpResponse = httpClient.get(url) {
            method = HttpMethod.Get
        }

        val responseBody: String = response.readText()

        return withContext(Dispatchers.Main) {
            podcastFeedParser.parse(responseBody)
        }
    }

    suspend fun postMediaToHistory(media: Media): Boolean {
        val response: HttpResponse = httpClient.post("$API_ENDPOINT/history") {
            contentType(ContentType.Application.Json)
            body = media
        }

        return response.status == HttpStatusCode.OK
    }

    suspend fun fetchMediaHistory(): List<Media> {
        return try {
            return httpClient.get("$API_ENDPOINT/history")
        } catch (e: Exception) {
            emptyList()
        }
    }
}
