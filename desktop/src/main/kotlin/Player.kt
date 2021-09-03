import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role.Companion.Button
import androidx.compose.ui.unit.dp

@Composable
fun Player() {
    Box(
        modifier = Modifier
            .padding(8.dp)
            .clip(shape = RoundedCornerShape(corner = CornerSize(10.dp)))
            .border(width = 1.dp, color = Color.Black, shape = RoundedCornerShape(10.dp))
    ) {
        PlayerScreen()
    }
}

@Composable
fun PlayerScreen() {
    //val screenState by playerScreenViewModel.state.collectAsState()

    PlayerScreen(
        "mediaHeaderName",
        "mediaName",
        "mediaDuration",
        "positionInMedia",
        "imageUri",
        false,
        { },
        { }
    )
}

@Composable
fun PlayerScreen(
    mediaHeaderName: String,
    mediaName: String,
    duration: String,
    positionInMedia: String,
    imageUri: String?,
    isPlaying: Boolean,
    pauseClickAction: () -> Unit,
    playClickAction: () -> Unit
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(20.dp)
    ) {

        Box(
            modifier = Modifier
                .size(200.dp)
                .clip(shape = RoundedCornerShape(10.dp))
                .background(color = Color(0xFFe63946))
        )

        Text(text = mediaHeaderName, modifier = Modifier.padding(top = 20.dp))

        Text(text = mediaName, modifier = Modifier.padding(top = 8.dp))

        LinearProgressIndicator(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 40.dp, bottom = 8.dp)
        )

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 20.dp)
        ) {
            Text(text = positionInMedia)

            Text(text = duration)
        }

        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 40.dp)
        ) {
            Button(onClick = {}) {
                Image(
                    painterResource("backward.png"),
                    contentDescription = "",
                    modifier = Modifier
                        .size(40.dp),
                )
            }

            if (isPlaying) {
                Button(onClick = {}) {
                    Image(
                        painterResource("pause.png"),
                        contentDescription = "",
                        modifier = Modifier
                            .size(60.dp)
                            .clickable {
                                pauseClickAction.invoke()
                            },
                    )
                }
            } else {
                Button(onClick = {}) {
                    Image(
                        painterResource("play.png"),
                        contentDescription = "",
                        modifier = Modifier
                            .size(60.dp)
                            .clickable {
                                playClickAction.invoke()
                            },
                    )
                }
            }

            Button(onClick = {}) {
                Image(
                    painterResource("fastforward.png"),
                    contentDescription = "",
                    modifier = Modifier
                        .size(40.dp),
                )
            }
        }
    }
}