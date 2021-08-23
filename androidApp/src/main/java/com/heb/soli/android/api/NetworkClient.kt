package com.heb.soli.android.api

import android.util.Log
import com.rometools.rome.io.SyndFeedInput
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.okhttp.*
import io.ktor.client.features.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.features.logging.*
import io.ktor.client.features.observer.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import org.xml.sax.InputSource
import java.io.StringReader
import java.lang.Exception

const val API_ENDPOINT = "https://music.hebus.net/api"

class NetworkClient {
    private val TAG = NetworkClient::class.simpleName

    private val httpClient = HttpClient(OkHttp) {
        install(DefaultRequest) {
            headers.append("Content-Type", "application/json")
        }

        install(JsonFeature) {
            serializer = KotlinxSerializer()
        }

        install(Logging) {
            logger = object : Logger {
                override fun log(message: String) {
                    Log.v("Logger Ktor =>", message)
                }

            }
            level = LogLevel.ALL
        }

        install(ResponseObserver) {
            onResponse { response ->
                Log.d("HTTP status:", "${response.status.value}")
            }
        }
    }

    private val syndFeedInput = SyndFeedInput()
    private val podcastFeedParser = PodcastFeedParser()

    suspend fun fetchAllRadios(): List<RadioStream> {
        Log.d(TAG, "fetch all radios")

        return try {
            httpClient.get("$API_ENDPOINT/radios")
        } catch (e: Exception) {
            Log.d(TAG, "error while fetching radios $e")
            emptyList()
        }
    }

    suspend fun fetchPodcastFeed(url: String): PodcastFeed {
        val response: HttpResponse = httpClient.get(url) {
            method = HttpMethod.Get
        }

        val responseBody: String = response.readText()
        val feed = syndFeedInput.build(InputSource(StringReader(responseBody)))

        return podcastFeedParser.parse(feed)
    }
}
