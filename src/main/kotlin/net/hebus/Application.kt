package net.hebus

import dorkbox.systemTray.SystemTray
import mu.KotlinLogging
import net.hebus.player.FakePlayer
import net.hebus.player.GStreamerPlayer
import net.hebus.provider.RadioProvider
import net.hebus.server.http.HttpServer
import net.hebus.service.MusicService
import net.hebus.service.TrayIconService

private val logger = KotlinLogging.logger {}

fun main(args: Array<String>) {
    logger.debug { "Starting Music Server" }
    val configuration = readConfiguration(args, System.getenv())

    val player = if (configuration.enablePlayer) {
        GStreamerPlayer()
    } else {
        FakePlayer()
    }

    val musicService = MusicService(player)
    val httpServer = HttpServer(configuration.port, musicService, RadioProvider())

    if (configuration.enablePlayer) {
        val trayIconService = TrayIconService(SystemTray.get(), musicService)
        trayIconService.loadTrayIcon()
    }

    httpServer.start()
}

fun readConfiguration(args: Array<String>, getenv: Map<String, String>): Configuration {
    val argsMap = mapOf(
        "--port" to "port",
        "--player" to "player"
    )

    val values = args.toList()
        .chunked(2)
        .map {
            argsMap[it[0]] to it[1]
        }.toMap()

    val port = values["port"]?.toInt() ?: 8080
    val enablePlayer = values["player"].toBoolean()
    return Configuration(port, enablePlayer)
}

data class Configuration(val port: Int = 8080, val enablePlayer: Boolean = false)

