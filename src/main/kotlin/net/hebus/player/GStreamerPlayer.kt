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

    override fun stop() {
        audioPlayBin.setState(State.NULL);
    }

    override fun load(filePath: String) {
        audioPlayBin.setInputFile(File("/home/flo/SBTRKT_EM.mp3"))
    }

    override fun play() {
        audioPlayBin.state = State.PLAYING;
        Gst.main();
    }

    override fun pause() {
        audioPlayBin.state = State.PAUSED;
    }
}