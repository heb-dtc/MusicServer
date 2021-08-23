package net.hebus.player

import org.freedesktop.gstreamer.ElementFactory
import org.freedesktop.gstreamer.Gst
import org.freedesktop.gstreamer.State
import org.freedesktop.gstreamer.elements.PlayBin
import java.io.File
import java.net.URI
import java.util.concurrent.TimeUnit

class GStreamerPlayer : Player {

    private var audioPlayBin: PlayBin

    init {
        Gst.init("musicserver")
        audioPlayBin = PlayBin("AudioPlayer")
        audioPlayBin.setVideoSink(ElementFactory.make("fakesink", "videosink"))
    }

    override fun getStatus(): PlayerStatus {
        if (audioPlayBin.state == State.PLAYING) {
            val track = audioPlayBin.get("uri")
            return PlayerStatus(audioPlayBin.state == State.PLAYING, track?.toString())
        }
        return PlayerStatus(false, "no track set")
    }

    fun getPosition(): Long {
        if (audioPlayBin.isPlaying) {
            return audioPlayBin.queryPosition(TimeUnit.MILLISECONDS)
        }
        return 0
    }

    fun getDuration(): Long {
        if (audioPlayBin.isPlaying) {
            return audioPlayBin.queryPosition(TimeUnit.MILLISECONDS)
        }
        return 0
    }

    override fun stop() {
        audioPlayBin.state = State.NULL
    }

    override fun load(filePath: String) {
        audioPlayBin.setInputFile(File(filePath))
    }

    override fun load(uri: URI) {
        audioPlayBin.setURI(uri)
    }

    override fun play() {
        audioPlayBin.state = State.PLAYING
        Gst.main()
    }

    override fun pause() {
        audioPlayBin.state = State.PAUSED
    }
}