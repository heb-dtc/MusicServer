package com.heb.soli.api

import io.ktor.client.*
import io.ktor.client.engine.okhttp.*

fun buildNetworkClient(): NetworkClient {
    return NetworkClient(HttpClient(OkHttp))
}