package com.heb.soli.android

import com.heb.soli.android.api.NetworkClient
import com.heb.soli.android.media.MediaRepository

class AppContainer {

    private val networkClient: NetworkClient = NetworkClient()

    val mediaRepository = MediaRepository(networkClient)
}
