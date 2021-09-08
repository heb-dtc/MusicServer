import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun PlayerView(playerViewModel: PlayerViewModel) {
    val playerState = playerViewModel.state.collectAsState()

    BoxWithConstraints(
        modifier = Modifier
            .padding(8.dp)
            .clip(shape = RoundedCornerShape(corner = CornerSize(10.dp)))
            .border(width = 2.dp, color = Color(0xFFF0F3F9), shape = RoundedCornerShape(10.dp))
    ) {
        PlayerControlView(
            playerState.value,
            playerViewModel::pausePlayback,
            playerViewModel::resumePlayback,
            this@BoxWithConstraints.maxWidth / 2
        )
    }
}

@Composable
fun PlayerControlView(
    playerState: PlayerViewState,
    onPause: () -> Unit,
    onResume: () -> Unit,
    maxWidth: Dp
) {
    PlayerControlView(
        playerState.mediaHeaderName,
        playerState.mediaName,
        playerState.mediaDuration,
        playerState.positionInMedia,
        playerState.imageUri,
        playerState.isPlaying,
        onPause,
        onResume,
        maxWidth
    )
}

@Composable
fun PlayerControlView(
    mediaHeaderName: String,
    mediaName: String,
    duration: String,
    positionInMedia: String,
    imageUri: String?,
    isPlaying: Boolean,
    pauseClickAction: () -> Unit,
    playClickAction: () -> Unit,
    maxWidth: Dp
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(20.dp)
    ) {

        if (imageUri != null) {
            NetworkImage(
                imageUri, modifier = Modifier
                    .size(maxWidth)
                    .clip(shape = RoundedCornerShape(corner = CornerSize(10.dp)))
                    .background(Color(0xFFe63946))
            )
        } else {
            Box(
                modifier = Modifier
                    .size(maxWidth)
                    .clip(shape = RoundedCornerShape(10.dp))
                    .background(color = Color(0xFFe63946))
            )
        }

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
                Button(onClick = { pauseClickAction.invoke() }) {
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
                Button(onClick = { playClickAction.invoke() }) {
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
                        .size(40.dp)
                )
            }
        }
    }
}