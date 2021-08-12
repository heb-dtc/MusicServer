package com.heb.soli

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.itemsIndexed
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
import com.heb.soli.ui.theme.SoliTheme

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DefaultPreview() {
    SoliTheme {
        RadioList(
            medias = listOf(
                Media(MediaId(1), "SOMA", "", MediaType.RADIO_STREAM),
                Media(MediaId(2), "FRANCE INFO", "", MediaType.RADIO_STREAM),
                Media(MediaId(3), "FRANCE INTER", "", MediaType.RADIO_STREAM),
                Media(MediaId(4), "FIP", "", MediaType.RADIO_STREAM),
            ),
            {}
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
internal fun RadioList(medias: List<Media>, onClick: (Media) -> Unit) {
    val colors = listOf(0xFFe63946, 0xFFf1faee, 0xFFa8dadc, 0xFF457b9d, 0xFF1d3557)

    LazyVerticalGrid(
        cells = GridCells.Fixed(2)
    ) {
        itemsIndexed(items = medias) { index, media ->
            val colorIndex = if (index >= colors.size) (colors.indices).random() else index
            RadioItem(media, Color(colors[colorIndex]), onClick)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ItemPreview() {
    RadioItem(
        media = Media(MediaId(1), "SOMA", "", MediaType.RADIO_STREAM), color =
        Color.Red,
        {}
    )
}

@Composable
fun RadioItem(media: Media, color: Color, onClick: (Media) -> Unit) {
    Column(
        modifier = Modifier
            .height(150.dp)
            .fillMaxSize()
            .clickable {
                onClick(media)
            }
            .background(color),
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