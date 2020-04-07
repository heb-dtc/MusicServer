package net.hebus.service

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import mu.KotlinLogging
import net.hebus.player.Player
import java.net.URI
import java.util.*
import kotlin.concurrent.schedule

private val logger = KotlinLogging.logger {}

enum class PlayerState {
    PLAYING,
    PAUSED,
    STOPPED
}

interface MusicServiceListener {
    fun playerStateUpdate(state: PlayerState)
}

class MusicService(private val player: Player) {

    private var state: PlayerState = PlayerState.STOPPED
    private val playbackTimer: Timer = Timer("PlaybackTimer", false)

    private val listeners: MutableList<MusicServiceListener> = mutableListOf()

    fun registerListener(listener: MusicServiceListener) {
        listeners.add(listener)
    }

    fun getPlayerStatus() = player.getStatus()

    fun play() {
        logger.debug { "play" }
        GlobalScope.launch {
            player.play()
        }

        state = PlayerState.PLAYING
        updateListeners()
    }

    private fun updateListeners() {
        listeners.map {
            it.playerStateUpdate(state)
        }
    }

    fun pause() {
        logger.debug { "pause" }
        state = PlayerState.PAUSED
        updateListeners()
    }

    fun stop() {
        logger.debug { "stop" }
        playbackTimer.cancel()
        this.player.stop()
        state = PlayerState.STOPPED
        updateListeners()
    }

    fun load(filePath: String) {
        logger.debug { "loading file $filePath" }
        this.player.load(filePath)
    }

    fun load(uri: URI) {
        logger.debug { "loading uri $uri" }
        this.player.load(uri)
        play()
    }
}