package com.heb.soli

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.heb.soli.api.Media
import com.heb.soli.media.MediaRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

private const val TAG = "RadioScreenViewModel"

class HomeViewModel(private val mediaRepository: MediaRepository) : ViewModel() {

    val radios: MutableState<List<Media>> = mutableStateOf(emptyList())

    init {
        viewModelScope.launch {
            mediaRepository.getRadioList().collect {
                Log.d(TAG, "${it.size} radios found")

                withContext(Dispatchers.Main) {
                    radios.value = it
                }
            }
        }
    }
}
