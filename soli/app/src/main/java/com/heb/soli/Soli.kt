package com.heb.soli

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.heb.soli.ui.theme.SoliTheme

@Composable
fun Soli(
    homeViewModel: HomeViewModel, podcastFeedViewModel: PodcastFeedViewModel,
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
                        TopAppBar(title = { Text(text = "SOLI") })
                        HomeScreen(homeViewModel = homeViewModel)
                    }
                }

                composable("podcastFeed/{feed_title}") { backStackEntry ->
                    //TODO: maybe extract the episodes already here?
                    val feedTitle = backStackEntry.arguments?.getString("feed_title") ?: ""

                    Column {
                        TopAppBar(title = { Text(text = feedTitle) }, navigationIcon = {
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
            }
        }
    }
}