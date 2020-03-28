package net.hebus.service

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import mu.KotlinLogging
import net.hebus.player.Player
import java.net.URI

private val logger = KotlinLogging.logger {}

class MusicService(private val player: Player) {

    fun getPlayerStatus() = player.getStatus()

    fun play() {
        logger.debug { "play"}
        GlobalScope.launch {
            player.play()
        }
    }

    fun pause() {
        logger.debug { "pause"}
        this.player.pause()
    }

    fun stop() {
        logger.debug { "stop"}
        this.player.stop()
    }

    fun load(filePath: String) {
        logger.debug { "loading file $filePath"}
        this.player.load(filePath)
    }

    fun load(uri: URI) {
        logger.debug { "loading uri $uri"}
        this.player.load(uri)
    }
}