package net.hebus

import net.hebus.player.GStreamerPlayer
import net.hebus.player.PlayerController
import net.hebus.server.http.HttpServer
import org.freedesktop.gstreamer.Gst
import org.freedesktop.gstreamer.ElementFactory
import org.freedesktop.gstreamer.State
import org.freedesktop.gstreamer.elements.PlayBin
import java.io.File


fun main(args: Array<String>) {
    println("Starting Music Server")

    val player = GStreamerPlayer()
    player.load("/Users/flow/dev/battle_music.mp3")

    val playerController = PlayerController(player)

    val httpServer = HttpServer(playerController)
    httpServer.start()

    /*
    player.load("/home/flo/SBTRKT_EM.mp3")
    player.play()*/
}

