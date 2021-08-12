package com.heb.soli

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.ui.Modifier
import androidx.core.view.WindowCompat
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.heb.soli.api.Media
import com.heb.soli.media.MediaRepository
import com.heb.soli.ui.theme.SoliTheme

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
                PodcastFeedViewModel(mediaRepository, navController)

            Soli(
                homeViewModel = homeViewModel,
                podcastFeedViewModel = podcastFeedViewModel,
                navController = navController
            )
            /*mediaNameView.setOnClickListener {
                val intent = Intent(applicationContext, PlayerActivity::class.java)
                startActivity(intent)
            }*/
        }
    }

    private fun playRadio(radio: Media) {
        startService(PlayerService.buildPlayIntent(baseContext, radio))
    }
}