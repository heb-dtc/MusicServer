import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.singleWindowApplication
import com.heb.soli.MediaRepository
import com.heb.soli.api.PodcastEpisode
import com.heb.soli.api.PodcastFeed
import com.heb.soli.api.RadioStream
import com.heb.soli.api.buildNetworkClient
import org.jetbrains.skia.Image
import java.io.ByteArrayOutputStream
import java.net.HttpURLConnection
import java.net.URL
import javax.imageio.ImageIO

@Composable
fun SplashUI() {
    Box(Modifier.fillMaxSize().background(Color.DarkGray)) {
        Text(
            "SOLI",
            Modifier.align(Alignment.Center),
            color = Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = 100.sp
        )
    }
}

fun loadNetworkImage(link: String): ImageBitmap? {
    println("loading image $link")
    val url = URL(link)
    val connection = url.openConnection() as HttpURLConnection
    connection.connect()

    val inputStream = connection.inputStream
    val bufferedImage = ImageIO.read(inputStream)

    return if (bufferedImage != null) {
        val stream = ByteArrayOutputStream()
        ImageIO.write(bufferedImage, "png", stream)
        val byteArray = stream.toByteArray()

        Image.makeFromEncoded(byteArray).asImageBitmap()
    } else {
        null
    }
}

fun main() = singleWindowApplication(title = "Soli") {

    val player = Player()
    val mediaRepository = MediaRepository(buildNetworkClient())

    // need to remember the VMs since the main() will be called again when the state changes
    val viewModel = remember {
        AppViewModel(mediaRepository, player)
    }
    val playerViewModel = remember {
        PlayerViewModel(player, mediaRepository)
    }

    // subscribe to app state
    val appState = viewModel.state.collectAsState()

    if (appState.value.loading) {
        SplashUI()
    } else {
        // TODO break this into tiner pieces
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
                                    border = BorderStroke(2.dp, Color.LightGray),
                                    shape = RoundedCornerShape(5.dp)
                                )
                        ) {

                            Text(
                                text = "Radio",
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(16.dp),
                                fontSize = 24.sp
                            )

                            RadioRow(appState.value.radios, viewModel::playRadio)
                        }

                        // Podcast list
                        Column(
                            modifier = Modifier.padding(12.dp)
                                .border(
                                    border = BorderStroke(2.dp, Color.LightGray),
                                    shape = RoundedCornerShape(5.dp)
                                )
                        ) {

                            Text(
                                text = "Podcast",
                                modifier = Modifier.padding(16.dp),
                                fontWeight = FontWeight.Bold,
                                fontSize = 24.sp
                            )

                            PodcastRow(appState.value.podcastList, viewModel::loadPodcastFeed)
                        }

                        // Podcast episodes
                        Column(
                            modifier = Modifier.padding(12.dp)
                                .fillMaxSize()
                                .border(
                                    border = BorderStroke(2.dp, Color.LightGray),
                                    shape = RoundedCornerShape(5.dp)
                                )
                        ) {

                            Text(
                                text = "Podcast Episode",
                                modifier = Modifier.padding(16.dp),
                                fontWeight = FontWeight.Bold,
                                fontSize = 24.sp
                            )

                            if (appState.value.episodes.isNotEmpty()) {
                                PodcastEpisodeList(
                                    appState.value.episodes,
                                    viewModel::playPodcastEpisode
                                )
                            }
                        }
                    }

                    Divider(
                        modifier = Modifier.fillMaxHeight().width(2.dp),
                        color = Color.LightGray
                    )

                    Column(
                        modifier = Modifier.padding(16.dp).fillMaxHeight().weight(0.25f),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        PlayerView(playerViewModel)
                    }
                }
            }
        }
    }
}

@Composable
fun PodcastEpisodeList(
    episodes: List<PodcastEpisode>,
    playEpisodeAction: (PodcastEpisode) -> Unit
) {
    LazyColumn(modifier = Modifier.padding(8.dp)) {
        itemsIndexed(episodes.subList(0, 5)) { _, episode ->
            PodcastEpisodeRow(episode, playEpisodeAction)
        }
    }
}

@Composable
fun PodcastEpisodeRow(episode: PodcastEpisode, playEpisodeAction: (PodcastEpisode) -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center
    ) {
        NetworkImage(
            episode.imageUrl, modifier = Modifier
                .size(80.dp)
                .padding(12.dp)
                .clip(shape = RoundedCornerShape(corner = CornerSize(15.dp)))
                .background(Color(0xFFe63946))
        )
        Text(
            text = episode.title,
            maxLines = 2,
            modifier = Modifier.weight(3f),
            style = MaterialTheme.typography.body2,
        )
        OutlinedButton(
            onClick = { },
            border = BorderStroke(1.dp, MaterialTheme.colors.primary),
            modifier = Modifier
                .padding(8.dp)
                .size(40.dp)
        ) {
            Image(
                painterResource("play.png"),
                contentDescription = "",
                modifier = Modifier
                    .size(60.dp)
                    .clickable {
                        playEpisodeAction.invoke(episode)
                    },
            )
        }
    }
}

@Composable
fun RadioRow(radios: List<RadioStream>, playRadioAction: (RadioStream) -> Unit) {
    val colors = listOf(0xFFe63946, 0xFFf1faee, 0xFFa8dadc, 0xFF457b9d, 0xFF1d3557)

    LazyRow(modifier = Modifier.padding(start = 8.dp, end = 8.dp, bottom = 16.dp).fillMaxWidth()) {
        itemsIndexed(items = radios) { index, radio ->
            val colorIndex = if (index >= colors.size) (colors.indices).random() else index

            RadioItem(radio, Color(colors[colorIndex]), playRadioAction)
        }
    }
}

@Composable
fun PodcastRow(podcastFeeds: List<PodcastFeed>, loadPodcastFeed: (List<PodcastEpisode>) -> Unit) {
    LazyRow(modifier = Modifier.padding(start = 8.dp, end = 8.dp, bottom = 16.dp).fillMaxWidth()) {
        itemsIndexed(items = podcastFeeds) { _, podcast ->
            PodcastItem(podcast, loadPodcastFeed)
        }
    }
}

@Composable
fun PodcastItem(feed: PodcastFeed, loadPodcastFeed: (List<PodcastEpisode>) -> Unit) {
    Column(
        modifier = Modifier.padding(8.dp)
            .border(width = 2.dp, color = Color.LightGray, shape = RoundedCornerShape(5.dp))
    ) {
        NetworkImage(feed.imageUrl, modifier = Modifier
            .size(140.dp)
            .padding(12.dp)
            .clip(shape = RoundedCornerShape(corner = CornerSize(15.dp)))
            .background(Color(0xFFe63946))
            .clickable {
                loadPodcastFeed.invoke(feed.episodes)
            })
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
fun RadioItem(radio: RadioStream, color: Color, onClick: (RadioStream) -> Unit) {
    Column(
        modifier = Modifier.padding(8.dp)
            .border(width = 2.dp, color = Color.LightGray, shape = RoundedCornerShape(5.dp))
    ) {
        Box(
            modifier = Modifier
                .size(140.dp)
                .padding(12.dp)
                .clip(shape = RoundedCornerShape(corner = CornerSize(15.dp)))
                .background(color)
                .clickable {
                    onClick(radio)
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