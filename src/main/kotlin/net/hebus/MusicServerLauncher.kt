package net.hebus

import mu.KotlinLogging
import net.hebus.player.GStreamerPlayer
import net.hebus.server.http.HttpServer
import net.hebus.service.MusicService

private val logger = KotlinLogging.logger {}

fun main(args: Array<String>) {
    logger.debug {  "Starting Music Server" }

    val musicService = MusicService(GStreamerPlayer())

    val httpServer = HttpServer(musicService)
    httpServer.start()
}

