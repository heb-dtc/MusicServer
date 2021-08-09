package com.heb.soli

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Tab
import androidx.compose.material.TabPosition
import androidx.compose.material.TabRow
import androidx.compose.material.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.heb.soli.api.Media
import com.heb.soli.api.MediaId
import com.heb.soli.api.MediaType
import com.heb.soli.api.PodcastFeed

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun HomePreview() {
    HomeScreen(
        radios = listOf(
            Media(MediaId(1), "SOMA", "", MediaType.RADIO_STREAM),
            Media(MediaId(2), "FRANCE INFO", "", MediaType.RADIO_STREAM),
            Media(MediaId(3), "FRANCE INTER", "", MediaType.RADIO_STREAM),
            Media(MediaId(4), "FIP", "", MediaType.RADIO_STREAM),
        ),
        podcasts = listOf(
            PodcastFeed(name = "afterhate", imageUrl = "", emptyList())
        ),
        selectedSection = HomeSection.Radio ,
        {}
    )
}

@Composable
fun HomeScreen(homeViewModel: HomeViewModel) {
    val radios = homeViewModel.radios.value
    val podcasts = homeViewModel.podcasts.value
    val section = homeViewModel.selectedSection.value

    HomeScreen(radios = radios, podcasts = podcasts, selectedSection = section, homeViewModel::onSectionSelected)
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(
    radios: List<Media>,
    podcasts: List<PodcastFeed>,
    selectedSection: HomeSection,
    onSectionSelected: (HomeSection) -> Unit
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
            Tab(selected = true, onClick = { onSectionSelected(HomeSection.Radio) },
                text = {
                    Text(text = "Radios")
                })

            Tab(selected = false, onClick = { onSectionSelected(HomeSection.Podcast) },
                text = {
                    Text(text = "Podcasts")
                })
        }

        when (selectedSection) {
            HomeSection.Radio -> {
                RadioList(medias = radios)
            }
            HomeSection.Podcast -> {
                PodcastList(medias = podcasts)
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
            .background(Color.Red, RoundedCornerShape(topStartPercent = 100, topEndPercent = 100))
    )
}

//TODO: extract to dedicated file
@ExperimentalFoundationApi
@Composable
fun PodcastList(medias: List<PodcastFeed>) {
    LazyVerticalGrid(
        cells = GridCells.Fixed(2)
    ) {
        itemsIndexed(items = medias) { index, media ->
            Column(
                modifier = Modifier
                    .height(150.dp)
                    .fillMaxSize()
                    .background(Color.DarkGray),
                verticalArrangement = Arrangement.Top,
            ) {
                Text(
                    media.name,
                    Modifier
                        .weight(1f)
                        .padding(8.dp)
                        .fillMaxWidth()
                        .wrapContentHeight(),
                    fontWeight = FontWeight.Bold,
                    fontSize = 32.sp
                )
            }
        }
    }
}