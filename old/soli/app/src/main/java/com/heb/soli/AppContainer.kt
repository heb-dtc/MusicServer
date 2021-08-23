package com.heb.soli

import com.heb.soli.api.NetworkClient
import com.heb.soli.media.MediaRepository

class AppContainer {

    private val networkClient: NetworkClient = NetworkClient()

    val mediaRepository = MediaRepository(networkClient)
}
