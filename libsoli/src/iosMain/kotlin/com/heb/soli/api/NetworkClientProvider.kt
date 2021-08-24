package com.heb.soli.api

import io.ktor.client.*
import io.ktor.client.engine.ios.*

fun buildNetworkClient(): NetworkClient {
    return NetworkClient(HttpClient(Ios))
}