package com.heb.soli.api

import io.ktor.client.*
import io.ktor.client.engine.apache.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.features.logging.*

fun buildNetworkClient(): NetworkClient {
    return NetworkClient(HttpClient(Apache) {
        install(JsonFeature) {
            serializer = KotlinxSerializer()
        }

        install(Logging) {
            logger = object : Logger {
                override fun log(message: String) {
                    // OFF for now
                    //Log.d("NetworkClient", message)
                }

            }
            level = LogLevel.ALL
        }
    })
}