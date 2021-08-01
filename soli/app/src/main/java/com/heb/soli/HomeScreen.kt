package com.heb.soli

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Tab
import androidx.compose.material.TabPosition
import androidx.compose.material.TabRow
import androidx.compose.material.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.heb.soli.api.Media
import com.heb.soli.api.MediaId
import com.heb.soli.api.MediaType

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun HomePreview() {
    HomeScreen(radios = listOf(
        Media(MediaId(1), "SOMA", "", MediaType.RADIO_STREAM),
        Media(MediaId(2), "FRANCE INFO", "", MediaType.RADIO_STREAM),
        Media(MediaId(3), "FRANCE INTER", "", MediaType.RADIO_STREAM),
        Media(MediaId(4), "FIP", "", MediaType.RADIO_STREAM),
    ))
}

@Composable
fun HomeScreen(homeViewModel: HomeViewModel) {
    val radios = homeViewModel.radios.value
    HomeScreen(radios = radios)
}

@Composable
fun HomeScreen(radios: List<Media>) {
    val selectedIndex = 0;
    val indicator = @Composable { tabPositions: List<TabPosition> ->
        HomeTabIndicator(
            Modifier.tabIndicatorOffset(tabPositions[selectedIndex])
        )
    }

    Column {
        TabRow(selectedTabIndex = 0, indicator = indicator) {
            Tab(selected = true, onClick = { /*TODO*/ },
                text = {
                    Text(text = "Radios")
                })

            Tab(selected = false, onClick = { /*TODO*/ },
                text = {
                    Text(text = "Podcasts")
                })
        }

        RadioList(medias = radios)
    }
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