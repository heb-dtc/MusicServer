package com.heb.soli.android.media.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.heb.soli.MediaRepository
import com.heb.soli.api.Media
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HistoryScreenViewModel(private val mediaRepository: MediaRepository) : ViewModel() {

    private val _state: MutableStateFlow<List<Media>> = MutableStateFlow(emptyList())

    val state get() = _state

    init {
        viewModelScope.launch {
            mediaRepository.getMediaHistoryList().collect {
                withContext(Dispatchers.Main) {
                    _state.value = it
                }
            }
        }
    }
}
