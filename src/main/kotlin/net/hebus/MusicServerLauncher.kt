package net.hebus

import org.freedesktop.gstreamer.Gst
import org.freedesktop.gstreamer.ElementFactory
import org.freedesktop.gstreamer.State
import org.freedesktop.gstreamer.elements.PlayBin
import java.io.File


fun main(args: Array<String>) {
    println("Starting Music Server")

    Gst.init()

    val playbin = PlayBin("AudioPlayer")
    playbin.setVideoSink(ElementFactory.make("fakesink", "videosink"))
    playbin.setInputFile(File("/home/flo/SBTRKT_EM.mp3"))

    playbin.setState(State.PLAYING);
    Gst.main();
    playbin.setState(State.NULL);
}

