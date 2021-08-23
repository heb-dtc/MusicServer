package com.heb.soli.android.media.ui

import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import com.heb.soli.android.api.PodcastEpisode
import com.heb.soli.android.media.MediaRepository

class PodcastFeedViewModel(
    private val mediaRepository: MediaRepository,
    private val navController: NavHostController,
    private val playAction: (PodcastEpisode) -> Unit
) : ViewModel() {

    fun getFeedEpisode(feedTitle: String): List<PodcastEpisode> {
        return mediaRepository.getPodcastFeed(title = feedTitle)?.episodes ?: emptyList()
    }

    fun playEpisode(podcastEpisode: PodcastEpisode) {
        playAction.invoke(podcastEpisode)
        navController.navigate("player")
    }
}