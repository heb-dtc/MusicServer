package net.hebus

import net.hebus.player.GStreamerPlayer
import net.hebus.server.http.HttpServer
import org.freedesktop.gstreamer.Gst
import org.freedesktop.gstreamer.ElementFactory
import org.freedesktop.gstreamer.State
import org.freedesktop.gstreamer.elements.PlayBin
import java.io.File


fun main(args: Array<String>) {
    println("Starting Music Server")

    val httpServer = HttpServer()
    httpServer.start()

    /*val player = GStreamerPlayer()
    player.load("/home/flo/SBTRKT_EM.mp3")
    player.play()*/
}

