package net.hebus.player

import org.freedesktop.gstreamer.ElementFactory
import org.freedesktop.gstreamer.Gst
import org.freedesktop.gstreamer.State
import org.freedesktop.gstreamer.elements.PlayBin
import java.io.File

class GStreamerPlayer : Player {

    private var audioPlayBin: PlayBin

    init {
        Gst.init("musicserver")
        audioPlayBin = PlayBin("AudioPlayer")
        audioPlayBin.setVideoSink(ElementFactory.make("fakesink", "videosink"))
    }

    override fun getStatus(): PlayerStatus {
        return PlayerStatus(audioPlayBin.state == State.PLAYING, "")
    }

    override fun stop() {
        audioPlayBin.state = State.NULL
    }

    override fun load(filePath: String) {
        audioPlayBin.setInputFile(File(filePath))
    }

    override fun play() {
        audioPlayBin.state = State.PLAYING
        Gst.main()
    }

    override fun pause() {
        audioPlayBin.state = State.PAUSED
    }
}