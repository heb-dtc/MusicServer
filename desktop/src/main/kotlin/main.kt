import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.singleWindowApplication
import com.heb.soli.MediaRepository
import com.heb.soli.api.PodcastFeed
import com.heb.soli.api.RadioStream
import com.heb.soli.api.buildNetworkClient

fun main() = singleWindowApplication {

    val viewModel = AppViewModel(MediaRepository(buildNetworkClient()))
    val appState = viewModel.state.collectAsState()

    MaterialTheme {
        Surface(modifier = Modifier.background(Color.White)) {

            // main window
            Row(modifier = Modifier.fillMaxSize()) {

                // Left panel
                Column(modifier = Modifier.padding(16.dp).weight(weight = 0.75f)) {

                    // Radio list
                    Column(
                        modifier = Modifier.padding(12.dp)
                            .border(
                                border = BorderStroke(2.dp, Color(0xFFedeff3)),
                                shape = RoundedCornerShape(5.dp)
                            )
                    ) {

                        Text(
                            text = "Radio",
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(16.dp),
                            fontSize = 24.sp
                        )

                        RadioRow(appState.value.radios)
                    }

                    // Podcast list
                    Column(
                        modifier = Modifier.padding(12.dp)
                            .border(
                                border = BorderStroke(2.dp, Color(0xFFedeff3)),
                                shape = RoundedCornerShape(5.dp)
                            )
                    ) {

                        Text(
                            text = "Podcast",
                            modifier = Modifier.padding(16.dp),
                            fontWeight = FontWeight.Bold,
                            fontSize = 24.sp
                        )

                        PodcastRow(appState.value.podcastList)
                    }
                }

                Divider(modifier = Modifier.fillMaxHeight().width(2.dp), color = Color(0xFFedeff3))

                Column(
                    modifier = Modifier.padding(16.dp).fillMaxHeight().weight(0.25f),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Player()
                }
            }
        }
    }
}

@Composable
fun RadioRow(radios: List<RadioStream>) {
    val colors = listOf(0xFFe63946, 0xFFf1faee, 0xFFa8dadc, 0xFF457b9d, 0xFF1d3557)

    LazyRow(modifier = Modifier.padding(start = 8.dp, end = 8.dp, bottom = 16.dp).fillMaxWidth()) {
        itemsIndexed(items = radios) { index, radio ->
            val colorIndex = if (index >= colors.size) (colors.indices).random() else index

            RadioItem(radio, Color(colors[colorIndex]))
        }
    }
}

@Composable
fun PodcastRow(podcastFeeds: List<PodcastFeed>) {
    LazyRow(modifier = Modifier.padding(start = 8.dp, end = 8.dp, bottom = 16.dp).fillMaxWidth()) {
        itemsIndexed(items = podcastFeeds) { _, podcast ->
            PodcastItem(podcast)
        }
    }
}

@Composable
fun PodcastItem(feed: PodcastFeed) {
    Column(
        modifier = Modifier.padding(8.dp)
            .border(width = 2.dp, color = Color(0xFFedeff3), shape = RoundedCornerShape(5.dp))
    ) {
        Box(
            modifier = Modifier
                .size(140.dp)
                .padding(12.dp)
                .clip(shape = RoundedCornerShape(corner = CornerSize(15.dp)))
                .background(Color(0xFFe63946))
                .clickable {
                    //onClick(radio)
                },
        )
        Text(
            feed.name,
            Modifier
                .padding(start = 12.dp, bottom = 12.dp, end = 12.dp),
            fontWeight = FontWeight.Bold,
            fontSize = 12.sp,
        )
    }
}

@Composable
fun RadioItem(radio: RadioStream, color: Color) {
    Column(
        modifier = Modifier.padding(8.dp)
            .border(width = 2.dp, color = Color(0xFFedeff3), shape = RoundedCornerShape(5.dp))
    ) {
        Box(
            modifier = Modifier
                .size(140.dp)
                .padding(12.dp)
                .clip(shape = RoundedCornerShape(corner = CornerSize(15.dp)))
                .background(color)
                .clickable {
                    //onClick(radio)
                },
        )

        Text(
            radio.name,
            Modifier
                .padding(start = 12.dp, bottom = 12.dp, end = 12.dp),
            fontWeight = FontWeight.Bold,
            fontSize = 12.sp,
        )
    }
}