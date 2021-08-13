package com.heb.soli

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.compose.rememberNavController
import com.heb.soli.api.Media
import com.heb.soli.api.PodcastEpisode
import com.heb.soli.media.MediaRepository

class MainActivity : AppCompatActivity() {

    private val TAG = MainActivity::class.simpleName

    private lateinit var mediaRepository: MediaRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mediaRepository = (application as SoliApp).appContainer.mediaRepository

        setContent {
            val navController = rememberNavController()
            val homeViewModel =
                HomeViewModel(mediaRepository, navController, this::playRadio)
            val podcastFeedViewModel =
                PodcastFeedViewModel(mediaRepository, navController, this::playPodcast)
            val playerScreenViewModel = PlayerScreenViewModel(this::playerPlayPauseAction)

            Soli(
                homeViewModel = homeViewModel,
                podcastFeedViewModel = podcastFeedViewModel,
                playerScreenViewModel = playerScreenViewModel,
                navController = navController
            )
        }
    }

    private fun playRadio(radio: Media) {
        startService(PlayerService.buildPlayRadioIntent(baseContext, radio))
    }

    private fun playPodcast(feedTitle: String, episodeIndex: Int) {
        startService(PlayerService.buildPlayPodcastIntent(baseContext, feedTitle, episodeIndex))
    }

    private fun playerPlayPauseAction() {
        val intent = PlayerService.buildCommandIntent(baseContext, ARG_ACTION_PLAY_PAUSE)
        startService(intent)
    }
}