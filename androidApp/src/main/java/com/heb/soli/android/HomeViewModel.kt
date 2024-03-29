package com.heb.soli.android

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavHostController
import com.heb.soli.api.PodcastFeed
import com.heb.soli.api.RadioStream
import com.heb.soli.MediaRepository
import com.heb.soli.api.toMedia
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

private const val TAG = "RadioScreenViewModel"

class HomeViewModel(
    private val mediaRepository: MediaRepository,
    private val navController: NavHostController,
    private val startRadioAction: (RadioStream) -> Unit
) : ViewModel() {

    val radios: MutableState<List<RadioStream>> = mutableStateOf(emptyList())
    val podcasts: MutableState<List<PodcastFeed>> = mutableStateOf(emptyList())
    val selectedSection: MutableState<HomeSection> = mutableStateOf(HomeSection.Radio)

    init {
        viewModelScope.launch {
            mediaRepository.getRadioList().collect {
                Log.d(TAG, "${it.size} radios found")

                withContext(Dispatchers.Main) {
                    radios.value = it
                }
            }

            mediaRepository.getPodcasts().collect {
                Log.d(TAG, "${it.size} podcasts found")

                withContext(Dispatchers.Main) {
                    podcasts.value = it
                }
            }
        }
    }

    fun onSectionSelected(section: HomeSection) {
        selectedSection.value = section
    }

    fun onOpenPodcastFeed(feed: PodcastFeed) {
        navController.navigate("podcastFeed/${feed.name}")
    }

    fun onStartRadio(radio: RadioStream) {
        startRadioAction.invoke(radio)

        viewModelScope.launch {
            mediaRepository.addMediaToHistoryList(radio.toMedia())
        }

        navController.navigate("player")
    }
}
