package net.hebus

import dorkbox.systemTray.SystemTray
import mu.KotlinLogging
import net.hebus.player.GStreamerPlayer
import net.hebus.server.http.HttpServer
import net.hebus.service.MusicService
import net.hebus.service.TrayIconService

private val logger = KotlinLogging.logger {}

fun main(args: Array<String>) {
    logger.debug {  "Starting Music Server" }

    val musicService = MusicService(GStreamerPlayer())
    val trayIconService = TrayIconService(SystemTray.get(), musicService)
    val httpServer = HttpServer(musicService)

    trayIconService.loadTrayIcon()
    httpServer.start()
}

