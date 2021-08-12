package com.heb.soli

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.heb.soli.api.PodcastEpisode

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PodcastFeedScreenPreview() {
    val episodes = listOf(
        PodcastEpisode(
            title = "Episode 133: cest qui le plus fort, shun ou musclor?i le plus fort, shun ou musclor?i le plus fort, shun ou musclor?",
            uri = "",
            imageUrl = ""
        ),
        PodcastEpisode(
            title = "Episode 133: cest qui le plus fort, shun ou musclor?",
            uri = "",
            imageUrl = ""
        ),
        PodcastEpisode(
            title = "Episode 133: cest qui le plus fort, shun ou musclor?",
            uri = "",
            imageUrl = ""
        ),
        PodcastEpisode(title = "Episode 133:", uri = "", imageUrl = ""),
        PodcastEpisode(
            title = "Episode 133: cest qui le plus fort, shun ou musclor?",
            uri = "",
            imageUrl = ""
        ),
        PodcastEpisode(
            title = "Episode 133: cest qui le plus fort, shun ou musclor?",
            uri = "",
            imageUrl = ""
        ),
        PodcastEpisode(
            title = "Episode 133: cest qui le plclor?",
            uri = "",
            imageUrl = ""
        ),
        PodcastEpisode(
            title = "Episode 133: cest qui le plus fort, shun ou musclor?",
            uri = "",
            imageUrl = ""
        ),
    )
    PodcastFeedScreen(episodes = episodes)
}

@Composable
fun PodcastFeedScreen(
    feedTitle: String,
    podcastFeedViewModel: PodcastFeedViewModel,
) {
    val episodes = podcastFeedViewModel.getFeedEpisode(feedTitle = feedTitle)
    PodcastFeedScreen(episodes = episodes)
}

@Composable
fun PodcastFeedScreen(episodes: List<PodcastEpisode>) {
    LazyColumn(contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)) {
        itemsIndexed(episodes) { index, episode ->
            EpisodeRow(index, episode)
        }
    }
}

@Composable
fun EpisodeRow(index: Int, episode: PodcastEpisode) {
    Row(
        verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.End
    ) {
        Text(
            text = "$index",
            style = MaterialTheme.typography.caption,
            fontWeight = FontWeight.W600,
            color = MaterialTheme.colors.primary
        )
        Image(
            painter = rememberImagePainter(data = episode.imageUrl, builder = {
                placeholder(R.drawable.ic_launcher_background)
            }),
            contentDescription = "",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .clip(RoundedCornerShape(10.dp))
                .size(48.dp)
                .padding(8.dp)
        )
        Text(
            text = episode.title,
            maxLines = 2,
            modifier = Modifier.weight(3f),
            style = MaterialTheme.typography.body2,
        )
        OutlinedButton(
            onClick = { /*TODO*/ },
            border = BorderStroke(1.dp, MaterialTheme.colors.primary),
            modifier = Modifier.padding(2.dp)
                .size(40.dp)
        ) {
            Image(
                bitmap = ImageBitmap.imageResource(
                    LocalContext.current.resources,
                    R.drawable.play
                ),
                contentDescription = "",
                modifier = Modifier
                    .size(20.dp),
                colorFilter = ColorFilter.tint(MaterialTheme.colors.primary)
            )
        }
    }
}
