import com.heb.soli.MediaRepository
import com.heb.soli.api.MediaType
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

data class PlayerViewState(
    val mediaHeaderName: String = "",
    val mediaName: String = "",
    val mediaDuration: String = "00:00:00",
    val positionInMedia: String = "00:00:00",
    val imageUri: String? = null,
    val isPlaying: Boolean = false
)

class PlayerViewModel(private val player: Player, private val mediaRepository: MediaRepository) {

    private val _state = MutableStateFlow(PlayerViewState())
    val state get() = _state

    init {
        GlobalScope.launch {
            player.playerContext.collect {

                println("update from player received -> ${it.media.name} / ${it.isPlaying}")

                when(it.media.type) {
                    MediaType.RADIO_STREAM -> {
                        val radio = mediaRepository.getRadio(it.media.id)
                        radio?.let { radioStream ->
                            _state.emit(PlayerViewState(
                                mediaHeaderName = "Radio",
                                mediaName = radioStream.name,
                                mediaDuration = "",
                                positionInMedia = "",
                                imageUri = null,
                                isPlaying = it.isPlaying
                            ))
                        }
                    }
                    MediaType.PODCAST_EPISODE -> {
                        val podcastEpisode = mediaRepository.getPodcastEpisode(it.media.id)
                        podcastEpisode?.let { episode ->
                            _state.value = PlayerViewState(
                                mediaHeaderName = "Podcast",
                                mediaName = episode.title,
                                mediaDuration = "",
                                positionInMedia = "",
                                imageUri = episode.imageUrl,
                                isPlaying = it.isPlaying
                            )
                        }
                    }
                    MediaType.TRACK -> TODO()
                    MediaType.NO_MEDIA -> {
                        _state.emit(PlayerViewState(
                            mediaHeaderName = "NO_MEDIA",
                            mediaName = "NO_MEDIA",
                            mediaDuration = "",
                            positionInMedia = "",
                            imageUri = null,
                            isPlaying = it.isPlaying
                        ))
                    }
                }
            }
        }
    }

    fun pausePlayback() {
        player.pause()
        GlobalScope.launch {
            _state.emit(_state.value.copy(isPlaying = false))
        }
    }

    fun resumePlayback() {
        player.resume()
        GlobalScope.launch {
            _state.emit(_state.value.copy(isPlaying = true))
        }
    }
}
