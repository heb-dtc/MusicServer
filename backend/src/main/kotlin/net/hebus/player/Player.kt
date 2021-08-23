package net.hebus.player

import java.net.URI

interface Player {
    fun load(filePath: String)
    fun load(uri: URI)
    fun play()
    fun pause()
    fun stop()
    fun getStatus(): PlayerStatus
}
