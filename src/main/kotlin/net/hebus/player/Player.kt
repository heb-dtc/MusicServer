package net.hebus.player

interface Player {
    fun load(filePath: String)
    fun play()
    fun pause()
    fun stop()
}