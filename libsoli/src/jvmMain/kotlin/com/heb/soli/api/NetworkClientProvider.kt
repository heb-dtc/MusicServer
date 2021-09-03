package com.heb.soli.api

import io.ktor.client.*
import io.ktor.client.engine.apache.*

fun buildNetworkClient(): NetworkClient {
    return NetworkClient(HttpClient(Apache))
}