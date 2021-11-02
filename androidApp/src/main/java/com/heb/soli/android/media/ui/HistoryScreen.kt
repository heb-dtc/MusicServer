package com.heb.soli.android.media.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import coil.compose.rememberImagePainter
import com.heb.soli.android.R
import com.heb.soli.api.Media

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun HistoryScreenPreview() {
    HistoryScreen(emptyList())
}

@Composable
fun HistoryScreen(historyScreenViewModel: HistoryScreenViewModel) {
    val history by historyScreenViewModel.state.collectAsState()
    HistoryScreen(history)
}

@Composable
fun HistoryScreen(history: List<Media>) {
    LazyColumn(contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp)) {
        itemsIndexed(history) { index, media ->
            HistoryItem(index, media)
        }
    }
}

@Composable
fun HistoryItem(index: Int, media: Media) {
    Card(
        modifier = Modifier
            .padding(12.dp),
        elevation = 2.dp,
        shape = RoundedCornerShape(corner = CornerSize(12.dp))
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(horizontal = 8.dp)
                .padding(top = 8.dp, bottom = 8.dp)
                .fillMaxWidth()
        ) {
            Image(
                painter = rememberImagePainter(
                    ContextCompat.getDrawable(
                        LocalContext.current,
                        R.drawable.img_placeholder
                    )
                ),
                contentDescription = "",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .clip(RoundedCornerShape(10.dp))
                    .size(32.dp)
            )

            Column {
                Text(
                    text = media.name,
                    maxLines = 2,
                    modifier = Modifier.padding(start = 8.dp),
                    style = MaterialTheme.typography.body1,
                )
            }
        }
    }
}