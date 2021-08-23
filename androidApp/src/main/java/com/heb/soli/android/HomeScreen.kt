package com.heb.soli.android

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.heb.soli.android.api.MediaId
import com.heb.soli.android.api.PodcastFeed
import com.heb.soli.android.api.RadioStream
import com.heb.soli.android.media.ui.RadioList

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun HomePreview() {
    HomeScreen(
        radios = listOf(
            RadioStream(MediaId("1"), "SOMA", ""),
            RadioStream(MediaId("2"), "FRANCE INFO", ""),
            RadioStream(MediaId("3"), "FRANCE INTER", ""),
            RadioStream(MediaId("4"), "FIP", ""),
        ),
        podcasts = listOf(
            PodcastFeed(
                id = "",
                name = "afterhate",
                imageUrl = "https://www.afterhate.fr/wp-content/uploads/2018/03/Episode57-vignette.jpg",
                emptyList()
            ),
            PodcastFeed(
                id = "",
                name = "afterhate",
                imageUrl = "https://www.afterhate.fr/wp-content/uploads/2018/03/Episode57-vignette.jpg",
                emptyList()
            ),
            PodcastFeed(
                id = "",
                name = "afterhate",
                imageUrl = "https://www.afterhate.fr/wp-content/uploads/2018/03/Episode57-vignette.jpg",
                emptyList()
            ),
            PodcastFeed(
                id = "",
                name = "afterhate",
                imageUrl = "https://www.afterhate.fr/wp-content/uploads/2018/03/Episode57-vignette.jpg",
                emptyList()
            ),
            PodcastFeed(
                id = "",
                name = "afterhate",
                imageUrl = "https://www.afterhate.fr/wp-content/uploads/2018/03/Episode57-vignette.jpg",
                emptyList()
            )
        ),
        selectedSection = HomeSection.Podcast,
        {},
        {},
        {}
    )
}

@Composable
fun HomeScreen(homeViewModel: HomeViewModel) {
    val radios = homeViewModel.radios.value
    val podcasts = homeViewModel.podcasts.value
    val section = homeViewModel.selectedSection.value

    HomeScreen(
        radios = radios,
        podcasts = podcasts,
        selectedSection = section,
        homeViewModel::onSectionSelected,
        onOpenPodcastFeed = homeViewModel::onOpenPodcastFeed,
        onStartRadio = homeViewModel::onStartRadio
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(
    radios: List<RadioStream>,
    podcasts: List<PodcastFeed>,
    selectedSection: HomeSection,
    onSectionSelected: (HomeSection) -> Unit,
    onOpenPodcastFeed: (PodcastFeed) -> Unit,
    onStartRadio: (RadioStream) -> Unit,
) {
    val selectedIndex = selectedSection.let {
        if (it == HomeSection.Radio) 0
        else 1
    }

    val indicator = @Composable { tabPositions: List<TabPosition> ->
        HomeTabIndicator(
            Modifier.tabIndicatorOffset(tabPositions[selectedIndex])
        )
    }

    Column {
        TabRow(selectedTabIndex = selectedIndex, indicator = indicator) {
            Tab(selected = selectedIndex == 0, onClick = { onSectionSelected(HomeSection.Radio) },
                text = {
                    Text(text = "Radios")
                })

            Tab(selected = selectedIndex == 1, onClick = { onSectionSelected(HomeSection.Podcast) },
                text = {
                    Text(text = "Podcasts")
                })
        }

        when (selectedSection) {
            HomeSection.Radio -> {
                RadioList(medias = radios, onClick = onStartRadio)
            }
            HomeSection.Podcast -> {
                PodcastList(medias = podcasts, onClick = onOpenPodcastFeed)
            }
        }
    }
}

enum class HomeSection {
    Radio, Podcast
}

@Composable
fun HomeTabIndicator(modifier: Modifier) {
    Spacer(
        modifier
            .padding(horizontal = 24.dp)
            .height(4.dp)
            .background(
                MaterialTheme.colors.background,
                RoundedCornerShape(topStartPercent = 100, topEndPercent = 100)
            )
    )
}

//TODO: extract to dedicated file
@ExperimentalFoundationApi
@Composable
fun PodcastList(
    medias: List<PodcastFeed>, onClick: (PodcastFeed) -> Unit
) {
    LazyVerticalGrid(
        cells = GridCells.Fixed(2),
        modifier = Modifier.padding(horizontal = 8.dp)
    ) {
        itemsIndexed(items = medias) { index, feed ->

            val modifier = if (index % 2 == 0) {
                Modifier.padding(start = 0.dp, end = 4.dp, top = 8.dp, bottom = 0.dp)
            } else {
                Modifier.padding(start = 4.dp, end = 0.dp, top = 8.dp, bottom = 0.dp)
            }

            Box(
                modifier = modifier
                    .height(140.dp)
                    .width(140.dp)
                    .clip(RoundedCornerShape(16.dp)),
            ) {
                if (feed.imageUrl.isEmpty()) {
                    Text(
                        feed.name,
                        Modifier
                            .padding(8.dp)
                            .fillMaxWidth()
                            .wrapContentHeight(),
                        fontWeight = FontWeight.Bold,
                        fontSize = 32.sp
                    )
                } else {
                    Image(
                        painter = rememberImagePainter(data = feed.imageUrl, builder = {
                            placeholder(R.drawable.img_placeholder)
                        }),
                        contentDescription = "",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxSize()
                            .clickable(onClick = {
                                onClick(feed)
                            }),
                    )
                }
            }
        }
    }
}