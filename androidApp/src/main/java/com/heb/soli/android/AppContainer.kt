package com.heb.soli.android

import com.heb.soli.api.NetworkClient
import com.heb.soli.android.media.MediaRepository
import com.heb.soli.api.buildNetworkClient

class AppContainer {

    private val networkClient: NetworkClient = buildNetworkClient()

    val mediaRepository = MediaRepository(networkClient)
}
