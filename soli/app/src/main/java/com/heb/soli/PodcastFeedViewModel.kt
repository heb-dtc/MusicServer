package com.heb.soli

import androidx.lifecycle.ViewModel
import com.heb.soli.api.PodcastEpisode
import com.heb.soli.media.MediaRepository

class PodcastFeedViewModel(private val mediaRepository: MediaRepository) : ViewModel() {

    fun getFeedEpisode(feedTitle: String): List<PodcastEpisode> {
        return mediaRepository.getPodcastFeed(title = feedTitle)?.episodes ?: emptyList()
    }
}