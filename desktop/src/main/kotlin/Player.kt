import com.heb.soli.api.Media
import com.heb.soli.api.NO_MEDIA
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import org.freedesktop.gstreamer.ElementFactory
import org.freedesktop.gstreamer.Gst
import org.freedesktop.gstreamer.State
import org.freedesktop.gstreamer.elements.PlayBin
import java.net.URI

data class PlayerContext(val media: Media, val isPlaying: Boolean)

class Player {

    private var audioPlayBin: PlayBin

    val playerContext = MutableStateFlow(PlayerContext(media = NO_MEDIA, isPlaying = false))

    init {
        Gst.init("SOLI")
        audioPlayBin = PlayBin("AudioPlayer")
        audioPlayBin.setVideoSink(ElementFactory.make("fakesink", "videosink"))
    }

    fun play(media: Media) {
        // clear current playback
        stop()

        audioPlayBin.setURI(URI.create(media.url))
        audioPlayBin.play()

        GlobalScope.launch {
            playerContext.emit(PlayerContext(media, true))
        }
    }

    fun resume() {
        audioPlayBin.play()
    }

    fun pause() {
        audioPlayBin.pause()
    }

    fun stop() {
        audioPlayBin.stop()
    }
}