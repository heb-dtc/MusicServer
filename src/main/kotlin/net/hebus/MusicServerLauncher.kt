package net.hebus

import net.hebus.player.GStreamerPlayer
import net.hebus.player.PlayerController
import net.hebus.server.http.HttpServer
import net.hebus.service.MusicService
import org.freedesktop.gstreamer.Gst
import org.freedesktop.gstreamer.ElementFactory
import org.freedesktop.gstreamer.State
import org.freedesktop.gstreamer.elements.PlayBin
import java.io.File


fun main(args: Array<String>) {
    println("Starting Music Server")

    val musicService = MusicService(GStreamerPlayer())

    val httpServer = HttpServer(musicService)
    httpServer.start()
}

