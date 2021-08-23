package com.heb.soli.player.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.heb.soli.api.MediaType
import com.heb.soli.media.MediaRepository
import com.heb.soli.player.PlayerService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

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
                                mediaDuration = "",
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

    fun play() {
        playPauseAction()
    }

    fun pause() {
        playPauseAction()
    }
}