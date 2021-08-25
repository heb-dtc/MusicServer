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
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import java.lang.Exception

const val API_ENDPOINT = "https://music.hebus.net/api"

class NetworkClient(private val httpClient: HttpClient) {
    private val TAG = NetworkClient::class.simpleName

    private val podcastFeedParser = PodcastFeedParser()

    init {
        httpClient.config {
            install(DefaultRequest) {
                headers.append("Content-Type", "application/json")
            }

            install(JsonFeature) {
                serializer = KotlinxSerializer()
            }

            install(Logging) {
                logger = object : Logger {
                    override fun log(message: String) {
                        //Log.v("Logger Ktor =>", message)
                    }

                }
                level = LogLevel.ALL
            }

            install(ResponseObserver) {
                onResponse { response ->
                    //Log.d("HTTP status:", "${response.status.value}")
                }
            }
        }
    }

    suspend fun fetchAllRadios(): List<RadioStream> {
        //Log.d(TAG, "fetch all radios")

        return try {
            //TODO this should work, the serializer is somehow not plugged well...
            //httpClient.get("$API_ENDPOINT/radios")
            val json = httpClient.get<String>("$API_ENDPOINT/radios")
            return Json.decodeFromString(json)
        } catch (e: Exception) {
            //Log.d(TAG, "error while fetching radios $e")
            emptyList()
        }
    }

    suspend fun fetchPodcastFeed(url: String): PodcastFeed {
        val response: HttpResponse = httpClient.get(url) {
            method = HttpMethod.Get
        }

        val responseBody: String = response.readText()

        return podcastFeedParser.parse(responseBody)
    }
}
