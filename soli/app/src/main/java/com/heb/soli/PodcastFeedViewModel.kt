package com.heb.soli

import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import com.heb.soli.api.PodcastEpisode
import com.heb.soli.media.MediaRepository

class PodcastFeedViewModel(
    private val mediaRepository: MediaRepository,
    private val navController: NavHostController,
    private val playAction: (String, Int) -> Unit
) : ViewModel() {

    fun getFeedEpisode(feedTitle: String): List<PodcastEpisode> {
        return mediaRepository.getPodcastFeed(title = feedTitle)?.episodes ?: emptyList()
    }

    fun playEpisode(episode: PodcastEpisode) {
        //FIXME
        //playAction.invoke()
        navController.navigate("player")
    }
}