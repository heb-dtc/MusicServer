package com.heb.soli.api

import android.util.Log
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.features.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.request.get
import io.ktor.util.KtorExperimentalAPI
import kotlinx.serialization.json.nonstrict
import java.lang.Exception

const val API_ENDPOINT = "http://192.168.1.11:8080/api" //"music.hebus.net/api"

class NetworkClient {
    private val TAG = NetworkClient::class.simpleName

    @KtorExperimentalAPI
    private val httpClient = HttpClient(CIO) {
        install(DefaultRequest) {
            headers.append("Content-Type", "application/json")
        }

        install(JsonFeature) {
            serializer = KotlinxSerializer()
        }
    }

    @KtorExperimentalAPI
    suspend fun fetchAllRadios(): List<Media> {
        Log.d(TAG, "fetch all radios")

        return try {
            httpClient.get("$API_ENDPOINT/radios")
        } catch (e: Exception) {
            Log.d(TAG, "error while fetching radios $e")
            emptyList()
        }
    }
}
