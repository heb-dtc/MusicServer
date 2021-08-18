package com.heb.soli

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.compose.rememberNavController
import com.heb.soli.api.PodcastEpisode
import com.heb.soli.api.RadioStream
import com.heb.soli.api.toMedia
import com.heb.soli.media.MediaRepository
import com.heb.soli.media.ui.PodcastFeedViewModel
import com.heb.soli.player.ARG_ACTION_PLAY_PAUSE
import com.heb.soli.player.PlayerService
import com.heb.soli.player.ui.PlayerScreenViewModel

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
            val playerScreenViewModel =
                PlayerScreenViewModel(mediaRepository, this::playerPlayPauseAction)

            Soli(
                homeViewModel = homeViewModel,
                podcastFeedViewModel = podcastFeedViewModel,
                playerScreenViewModel = playerScreenViewModel,
                navController = navController
            )
        }
    }

    private fun playRadio(radio: RadioStream) {
        startService(PlayerService.buildPlayMediaIntent(baseContext, radio.toMedia()))
    }

    private fun playPodcast(episode: PodcastEpisode) {
        startService(PlayerService.buildPlayMediaIntent(baseContext, episode.toMedia()))
    }

    private fun playerPlayPauseAction() {
        val intent = PlayerService.buildCommandIntent(baseContext, ARG_ACTION_PLAY_PAUSE)
        startService(intent)
    }
}