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
import com.heb.soli.api.buildNetworkClient
import com.heb.soli.api.RadioStream
import com.heb.soli.api.PodcastFeed
import com.heb.soli.MediaRepository

fun main() = singleWindowApplication {

    val viewModel = AppViewModel(MediaRepository(buildNetworkClient()))
    val appState = viewModel.state.collectAsState()

    MaterialTheme {

        // main window
        Row(modifier = Modifier.fillMaxSize()) {

            // Left panel
            Column(modifier = Modifier.padding(16.dp).weight(weight = 0.75f)) {

                // Radio list
                Column(
                    modifier = Modifier.border(
                        border = BorderStroke(1.dp, Color(0xff000000)),
                        shape = RoundedCornerShape(5.dp)
                    )
                ) {

                    Text(
                        text = "Radio",
                        modifier = Modifier.padding(8.dp),
                        fontWeight = FontWeight.Bold,
                        fontSize = 36.sp
                    )

                    RadioRow(appState.value.radios)
                }

                // Podcast list
                Column(
                    modifier = Modifier.padding(top = 24.dp)
                        .border(
                        border = BorderStroke(1.dp, Color(0xff000000)),
                        shape = RoundedCornerShape(5.dp)
                    )
                ) {

                    Text(
                        text = "Podcast",
                        modifier = Modifier.padding(8.dp),
                        fontWeight = FontWeight.Bold,
                        fontSize = 36.sp
                    )

                    PodcastRow(appState.value.podcastList)
                }
            }

            Divider(modifier = Modifier.fillMaxHeight().width(1.dp), color = Color.Black)

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

@Composable
fun RadioRow(radios: List<RadioStream>) {
    val colors = listOf(0xFFe63946, 0xFFf1faee, 0xFFa8dadc, 0xFF457b9d, 0xFF1d3557)

    LazyRow(modifier = Modifier.padding(8.dp)) {
        itemsIndexed(items = radios) { index, radio ->
            val colorIndex = if (index >= colors.size) (colors.indices).random() else index

            RadioItem(radio, Color(colors[colorIndex]))
        }
    }
}

@Composable
fun PodcastRow(podcastFeeds: List<PodcastFeed>) {
    LazyRow(modifier = Modifier.padding(8.dp)) {
        itemsIndexed(items = podcastFeeds) { _, podcast ->
            PodcastItem(podcast)
        }
    }
}

@Composable
fun PodcastItem(feed: PodcastFeed) {
    Box(
        modifier = Modifier
            .size(140.dp)
            .padding(8.dp)
            .clip(shape = RoundedCornerShape(corner = CornerSize(10.dp)))
            .background(Color(0xFFe63946))
            .clickable {
                //onClick(radio)
            },
    ) {
        Text(
            feed.name,
            Modifier
                .padding(8.dp)
                .align(Alignment.BottomStart),
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp,
        )
    }
}

@Composable
fun RadioItem(radio: RadioStream, color: Color) {
    Box(
        modifier = Modifier
            .size(140.dp)
            .padding(8.dp)
            .clip(shape = RoundedCornerShape(corner = CornerSize(10.dp)))
            .background(color)
            .clickable {
                //onClick(radio)
            },
    ) {
        Text(
            radio.name,
            Modifier
                .padding(8.dp)
                .align(Alignment.BottomStart),
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp,
        )
    }
}