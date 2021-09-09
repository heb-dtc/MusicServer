package com.heb.soli.android.player.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.heb.soli.MediaRepository
import com.heb.soli.android.player.PlayerService
import com.heb.soli.api.MediaType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.time.Duration
import java.time.LocalTime
import java.time.format.DateTimeFormatter

data class PlayerScreenState(
    val mediaHeaderName: String = "",
    val mediaName: String = "",
    val mediaDuration: String = "00:00:00",
    val positionInMedia: String = "00:00:00",
    val imageUri: String? = null,
    val isPlaying: Boolean = false
)

class PlayerScreenViewModel(
    private val mediaRepository: MediaRepository,
    val playPauseAction: () -> Unit
) : ViewModel() {

    private val _state = MutableStateFlow(PlayerScreenState())

    val state: MutableStateFlow<PlayerScreenState>
        get() = _state

    init {
        viewModelScope.launch {
            PlayerService.playerContext.collect {
                when (it.media.type) {
                    MediaType.RADIO_STREAM -> {
                        val radio = mediaRepository.getRadio(it.media.id)
                        radio?.let { radioStream ->
                            _state.value = PlayerScreenState(
                                mediaHeaderName = "Radio",
                                mediaName = radioStream.name,
                                mediaDuration = "",
                                positionInMedia = "",
                                imageUri = null,
                                isPlaying = it.isPlaying
                            )
                        }
                    }
                    MediaType.PODCAST_EPISODE -> {
                        val podcastEpisode = mediaRepository.getPodcastEpisode(it.media.id)
                        podcastEpisode?.let { episode ->
                            _state.value = PlayerScreenState(
                                mediaHeaderName = "Podcast",
                                mediaName = episode.title,
                                mediaDuration = formatDuration(episode.duration),
                                positionInMedia = "",
                                imageUri = episode.imageUrl,
                                isPlaying = it.isPlaying
                            )
                        }
                    }
                    MediaType.TRACK -> _state.value = PlayerScreenState(
                        mediaHeaderName = "Music",
                        mediaName = "HAS_MEDIA",
                        mediaDuration = "",
                        positionInMedia = "",
                        imageUri = null,
                        isPlaying = it.isPlaying
                    )
                    MediaType.NO_MEDIA -> _state.value = PlayerScreenState(
                        mediaHeaderName = "NO_MEDIA",
                        mediaName = "NO_MEDIA",
                        mediaDuration = "",
                        positionInMedia = "",
                        imageUri = null,
                        isPlaying = it.isPlaying
                    )
                }
            }
        }
    }

    private fun formatDuration(duration: Duration?): String {
        duration?.let {
            val time = LocalTime.of(
                duration.toHours().toInt(),
                duration.toMinutes().toInt() % 60,
                duration.seconds.toInt() % 60
            )
            return time.format(DateTimeFormatter.ofPattern("hh:mm:ss"))
        } ?: return ""
    }

    fun play() {
        playPauseAction()
    }

    fun pause() {
        playPauseAction()
    }
}