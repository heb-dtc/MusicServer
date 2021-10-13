import com.heb.soli.api.Media
import com.heb.soli.api.NO_MEDIA
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.freedesktop.gstreamer.ElementFactory
import org.freedesktop.gstreamer.Gst
import org.freedesktop.gstreamer.elements.PlayBin
import java.net.URI
import java.time.Duration
import java.util.concurrent.TimeUnit

data class PlayerContext(
    val media: Media,
    val isPlaying: Boolean,
    val currentPosition: Duration = Duration.ZERO
)

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
        startPlaybackPositionListener()

        GlobalScope.launch {
            playerContext.emit(PlayerContext(media, true))
        }
    }

    fun startPlaybackPositionListener() {
        GlobalScope.launch {
            repeat(1000) {
                val pos = queryPlaybackPosition()
                playerContext.emit(playerContext.value.copy(currentPosition = Duration.ofMillis(pos)))
            }
        }
    }

    private suspend fun queryPlaybackPosition(): Long {
        return withContext(Dispatchers.Main) {
            audioPlayBin.queryPosition(TimeUnit.MILLISECONDS)
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