package com.heb.soli

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.heb.soli.api.NO_MEDIA
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

data class PlayerScreenState(
    val mediaName: String = "",
    val mediaDuration: String = "00:00:00",
    val positionInMedia: String = "00:00:00",
    val isPlaying: Boolean = false
)

class PlayerScreenViewModel(val playPauseAction: () -> Unit) : ViewModel() {

    private val _state = MutableStateFlow(PlayerScreenState())

    val state: MutableStateFlow<PlayerScreenState>
        get() = _state

    init {
        viewModelScope.launch {
            PlayerService.playerContext.collect {
                if (it.mediaId == NO_MEDIA.id) {
                    _state.value = PlayerScreenState(mediaName = "NO_MEDIA", mediaDuration = "", positionInMedia= "", isPlaying = it.isPlaying)
                } else {
                    _state.value = PlayerScreenState(mediaName = "HAS_MEDIA", mediaDuration = "", positionInMedia= "", isPlaying = it.isPlaying)
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