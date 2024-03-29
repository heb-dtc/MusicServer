package com.heb.soli.android

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.heb.soli.android.media.ui.HistoryScreen
import com.heb.soli.android.media.ui.HistoryScreenViewModel
import com.heb.soli.android.media.ui.PodcastFeedScreen
import com.heb.soli.android.media.ui.PodcastFeedViewModel
import com.heb.soli.android.player.ui.PlayerScreen
import com.heb.soli.android.player.ui.PlayerScreenViewModel
import com.heb.soli.android.ui.theme.SoliTheme

@Composable
fun Soli(
    homeViewModel: HomeViewModel,
    podcastFeedViewModel: PodcastFeedViewModel,
    playerScreenViewModel: PlayerScreenViewModel,
    historyScreenViewModel: HistoryScreenViewModel,
    navController: NavHostController
) {
    SoliTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.background
        ) {
            //TODO extract routes and args
            NavHost(navController = navController, startDestination = "home") {
                composable("home") {
                    // A surface container using the 'background' color from the theme
                    Column {
                        TopAppBar(
                            title = { Text(text = "SOLI") },
                            backgroundColor = MaterialTheme.colors.primary,
                            actions = {
                                IconButton(onClick = { navController.navigate("history") }) {
                                    Icon(
                                        bitmap = ImageBitmap.imageResource(
                                            LocalContext.current.resources,
                                            R.drawable.history
                                        ),
                                        contentDescription = "go to history",
                                        modifier = Modifier.size(22.dp)
                                    )
                                }

                                IconButton(onClick = { navController.navigate("player") }) {
                                    Icon(
                                        bitmap = ImageBitmap.imageResource(
                                            LocalContext.current.resources,
                                            R.drawable.play
                                        ),
                                        contentDescription = "go to player",
                                        modifier = Modifier.size(22.dp)
                                    )
                                }
                            })
                        HomeScreen(homeViewModel = homeViewModel)
                    }
                }

                composable("podcastFeed/{feed_title}") { backStackEntry ->
                    //TODO: maybe extract the episodes already here?
                    val feedTitle = backStackEntry.arguments?.getString("feed_title") ?: ""

                    Column {
                        TopAppBar(
                            title = { Text(text = feedTitle) },
                            backgroundColor = MaterialTheme.colors.primary,
                            navigationIcon = {
                                IconButton(onClick = navController::popBackStack) {
                                    Icon(
                                        imageVector = Icons.Filled.ArrowBack,
                                        contentDescription = "back"
                                    )
                                }
                            })
                        PodcastFeedScreen(
                            feedTitle = feedTitle,
                            podcastFeedViewModel = podcastFeedViewModel,
                        )
                    }
                }

                composable("player") {
                    Column {
                        TopAppBar(
                            title = { Text(text = "Now Playing") },
                            backgroundColor = MaterialTheme.colors.primary,
                            navigationIcon = {
                                IconButton(onClick = navController::popBackStack) {
                                    Icon(
                                        imageVector = Icons.Filled.Close,
                                        contentDescription = "close"
                                    )
                                }
                            })
                        PlayerScreen(playerScreenViewModel = playerScreenViewModel)
                    }
                }

                composable("history") {
                    Column {
                        TopAppBar(
                            title = { Text(text = "History") },
                            backgroundColor = MaterialTheme.colors.primary,
                            navigationIcon = {
                                IconButton(onClick = navController::popBackStack) {
                                    Icon(
                                        imageVector = Icons.Filled.Close,
                                        contentDescription = "close"
                                    )
                                }
                            })
                        HistoryScreen(historyScreenViewModel = historyScreenViewModel)
                    }
                }
            }
        }
    }
}