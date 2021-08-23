package com.heb.soli.android.media.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.heb.soli.android.R
import com.heb.soli.android.api.MediaId
import com.heb.soli.android.api.PodcastEpisode

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PodcastFeedScreenPreview() {
    val episodes = listOf(
        PodcastEpisode(
            id = MediaId(""),
            playUri = "",
            title = "Episode 133: cest qui le plus fort, shun ou musclor?i le plus fort, shun ou musclor?i le plus fort, shun ou musclor?",
            uri = "",
            imageUrl = ""
        ),
        PodcastEpisode(
            id = MediaId(""),
            playUri = "",
            title = "Episode 133: cest qui le plus fort, shun ou musclor?",
            uri = "",
            imageUrl = ""
        ),
        PodcastEpisode(
            id = MediaId(""),
            playUri = "",
            title = "Episode 133: cest qui le plus fort, shun ou musclor?",
            uri = "",
            imageUrl = ""
        ),
        PodcastEpisode(
            id = MediaId(""),
            playUri = "",
            title = "Episode 133:",
            uri = "",
            imageUrl = ""
        ),
        PodcastEpisode(
            id = MediaId(""),
            playUri = "",
            title = "Episode 133: cest qui le plus fort, shun ou musclor?",
            uri = "",
            imageUrl = ""
        )
    )
    PodcastFeedScreen(feedTitle = "PodcastTitle", episodes = episodes, onClick = {})
}

@Composable
fun PodcastFeedScreen(
    feedTitle: String,
    podcastFeedViewModel: PodcastFeedViewModel,
) {
    val episodes = podcastFeedViewModel.getFeedEpisode(feedTitle = feedTitle)
    PodcastFeedScreen(feedTitle = feedTitle, episodes = episodes, podcastFeedViewModel::playEpisode)
}

@Composable
fun PodcastFeedScreen(
    feedTitle: String,
    episodes: List<PodcastEpisode>,
    onClick: (PodcastEpisode) -> Unit
) {
    LazyColumn(contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp)) {
        itemsIndexed(episodes) { index, episode ->
            EpisodeRowAlt(index, feedTitle, episode) {
                onClick(episode)
            }
        }
    }
}

@Composable
fun EpisodeRow(index: Int, episode: PodcastEpisode, onClick: () -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center
    ) {
        Image(
            painter = rememberImagePainter(data = episode.imageUrl, builder = {
                placeholder(R.drawable.img_placeholder)
            }),
            contentDescription = "",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .clip(RoundedCornerShape(10.dp))
                .size(64.dp)
                .padding(8.dp)
        )
        Text(
            text = episode.title,
            maxLines = 2,
            modifier = Modifier.weight(3f),
            style = MaterialTheme.typography.body2,
        )
        OutlinedButton(
            onClick = { onClick() },
            border = BorderStroke(1.dp, MaterialTheme.colors.primary),
            modifier = Modifier
                .padding(8.dp)
                .size(40.dp)
        ) {
            Image(
                bitmap = ImageBitmap.imageResource(
                    LocalContext.current.resources,
                    R.drawable.play
                ),
                contentDescription = "",
                colorFilter = ColorFilter.tint(MaterialTheme.colors.primary)
            )
        }
    }
}

@Preview
@Composable
fun EpisodeRowAltPreview() {
    EpisodeRowAlt(
        index = 0, feedTitle = "AfterHate", PodcastEpisode(
            id = MediaId(""),
            playUri = "",
            title = "Episode 133: cest qui le plus fort, shun ou musclor?",
            uri = "",
            imageUrl = ""
        )
    ) {}
}

@Composable
fun EpisodeRowAlt(index: Int, feedTitle: String, episode: PodcastEpisode, onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .padding(vertical = 4.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(MaterialTheme.colors.primary)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(horizontal = 8.dp)
                .padding(top = 8.dp)
        ) {
            Image(
                painter = rememberImagePainter(data = episode.imageUrl, builder = {
                    placeholder(R.drawable.img_placeholder)
                }),
                contentDescription = "",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .clip(RoundedCornerShape(10.dp))
                    .size(64.dp)
            )

            Column {
                Text(
                    text = episode.title,
                    maxLines = 2,
                    modifier = Modifier.padding(start = 8.dp),
                    style = MaterialTheme.typography.body1,
                    color = Color.White
                )

                Text(
                    text = feedTitle,
                    maxLines = 2,
                    modifier = Modifier.padding(start = 8.dp),
                    style = MaterialTheme.typography.body2,
                    color = Color.White
                )
            }
        }

        Divider(
            modifier = Modifier.padding(vertical = 12.dp, horizontal = 24.dp),
            color = MaterialTheme.colors.onBackground,
            thickness = 0.5.dp
        )

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp)
                .padding(bottom = 8.dp)
        ) {
            Text(
                text = "39:34:01mn",
                maxLines = 2,
                style = MaterialTheme.typography.body1,
                color = Color.White
            )

            OutlinedButton(
                onClick = { onClick() },
                border = BorderStroke(0.5.dp, MaterialTheme.colors.primary),
                modifier = Modifier
                    .size(40.dp)
            ) {
                Image(
                    bitmap = ImageBitmap.imageResource(
                        LocalContext.current.resources,
                        R.drawable.play
                    ),
                    contentDescription = "",
                    colorFilter = ColorFilter.tint(MaterialTheme.colors.primary)
                )
            }

        }
    }
}
