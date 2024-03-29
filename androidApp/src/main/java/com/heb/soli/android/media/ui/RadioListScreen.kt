package com.heb.soli.android.media.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.heb.soli.android.ui.theme.SoliTheme
import com.heb.soli.api.MediaId
import com.heb.soli.api.RadioStream

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
        cells = GridCells.Fixed(2),
        modifier = Modifier.padding(horizontal = 8.dp)
    ) {
        itemsIndexed(items = medias) { index, media ->
            val colorIndex = if (index >= colors.size) (colors.indices).random() else index

            val modifier = if (index % 2 == 0) {
                Modifier.padding(start = 0.dp, end = 4.dp, top = 8.dp, bottom = 0.dp)
            } else {
                Modifier.padding(start = 4.dp, end = 0.dp, top = 8.dp, bottom = 0.dp)
            }

            RadioItem(
                media, Color(colors[colorIndex]),
                modifier, onClick
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ItemPreview() {
    RadioItem(
        radio = RadioStream(MediaId("1"), "SOMA", ""), color =
        Color.Red,
        Modifier.padding(4.dp),
        {}
    )
}

@Composable
fun RadioItem(
    radio: RadioStream,
    color: Color,
    modifier: Modifier,
    onClick: (RadioStream) -> Unit
) {
    Card(elevation = 2.dp,
        shape = RoundedCornerShape(corner = CornerSize(12.dp)),
        backgroundColor = color,
        modifier = modifier
            .height(140.dp)
            .width(140.dp)
            .clickable {
                onClick(radio)
            }
    ) {
        Text(
            radio.name.uppercase(),
            Modifier
                .padding(8.dp),
            fontWeight = FontWeight.Bold,
            fontSize = 34.sp,
            color = Color.Black
        )
    }
}