package net.hebus.service

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import mu.KotlinLogging
import net.hebus.player.Player
import java.net.URI
import java.util.*
import kotlin.concurrent.schedule

private val logger = KotlinLogging.logger {}

class MusicService(private val player: Player) {

    private val playbackTimer: Timer = Timer("PlaybackTimer", false)

    fun getPlayerStatus() = player.getStatus()

    fun play() {
        logger.debug { "play" }

        GlobalScope.launch {
            player.play()
        }

        playbackTimer.schedule(500) {
            logPlayback()
        }
    }

    private fun logPlayback() {
        playbackTimer.schedule(500) {
            logger.debug { "isPlaying" }
            logPlayback()
        }
    }

    fun pause() {
        logger.debug { "pause" }
        playbackTimer.cancel()
        this.player.pause()
    }

    fun stop() {
        logger.debug { "stop" }
        playbackTimer.cancel()
        this.player.stop()
    }

    fun load(filePath: String) {
        logger.debug { "loading file $filePath" }
        this.player.load(filePath)
    }

    fun load(uri: URI) {
        logger.debug { "loading uri $uri" }
        this.player.load(uri)
    }
}