package com.heb.soli

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.PlayCircleFilled
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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
fun PodcastFeedScreen(episodes: List<PodcastEpisode>) {
    LazyColumn(contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)) {
        items(episodes) { episode ->
            EpisodeRow(episode)
        }
    }
}

@Composable
fun EpisodeRow(episode: PodcastEpisode) {
    Row(
        verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.End
    ) {
        Text(
            text = episode.title,
            maxLines = 2,
            modifier = Modifier.weight(2f)
        )
        Image(
            imageVector = Icons.Rounded.PlayCircleFilled,
            contentDescription = "",
            modifier = Modifier.size(40.dp)
        )
    }
}
