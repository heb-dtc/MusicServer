package com.heb.soli.media.ui

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
import com.heb.soli.api.MediaId
import com.heb.soli.api.RadioStream
import com.heb.soli.ui.theme.SoliTheme

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun DefaultPreview() {
    SoliTheme {
        RadioList(
            medias = listOf(
                RadioStream(MediaId("1"), "SOMA", ""),
                RadioStream(MediaId("2"), "FRANCE INFO", ""),
                RadioStream(MediaId("3"), "FRANCE INTER", ""),
                RadioStream(MediaId("4"), "FIP", ""),
            ),
            {}
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
internal fun RadioList(medias: List<RadioStream>, onClick: (RadioStream) -> Unit) {
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
        radio = RadioStream(MediaId("1"), "SOMA", ""), color =
        Color.Red,
        {}
    )
}

@Composable
fun RadioItem(radio: RadioStream, color: Color, onClick: (RadioStream) -> Unit) {
    Column(
        modifier = Modifier
            .height(150.dp)
            .fillMaxSize()
            .clickable {
                onClick(radio)
            }
            .background(color),
        verticalArrangement = Arrangement.Top,
    ) {
        Text(
            radio.name.uppercase(),
            Modifier
                .weight(1f)
                .padding(8.dp)
                .fillMaxWidth()
                .wrapContentHeight(),
            fontWeight = FontWeight.Bold,
            fontSize = 32.sp,
        )
    }
}