package com.heb.soli

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PlayerScreenPreview() {
    PlayerScreen(mediaName = "Super podcast", "01:23:90", "00:23:45", "", true, {}, {})
}

@Composable
fun PlayerScreen(playerScreenViewModel: PlayerScreenViewModel) {
    val screenState by playerScreenViewModel.state.collectAsState()

    PlayerScreen(
        screenState.mediaName,
        screenState.mediaDuration,
        screenState.positionInMedia,
        "",
        screenState.isPlaying,
        playerScreenViewModel::pause,
        playerScreenViewModel::play
    )
}

@Composable
fun PlayerScreen(
    mediaName: String,
    duration: String,
    positionInMedia: String,
    imageUri: String,
    isPlaying: Boolean,
    pauseClickAction: () -> Unit,
    playClickAction: () -> Unit
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(horizontal = 20.dp)
    ) {
        Image(
            painter = rememberImagePainter(data = imageUri, builder = {
                placeholder(R.drawable.ic_launcher_background)
            }),
            contentDescription = "",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(200.dp),
        )

        Text(text = mediaName, modifier = Modifier.padding(top = 20.dp))

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
            Image(
                bitmap = ImageBitmap.imageResource(
                    LocalContext.current.resources,
                    R.drawable.backward
                ),
                contentDescription = "",
                modifier = Modifier
                    .size(40.dp),
                colorFilter = ColorFilter.tint(MaterialTheme.colors.primary)
            )

            if (isPlaying) {
                Image(
                    bitmap = ImageBitmap.imageResource(
                        LocalContext.current.resources,
                        R.drawable.pause
                    ),
                    contentDescription = "",
                    modifier = Modifier
                        .size(60.dp)
                        .clickable {
                            pauseClickAction.invoke()
                        },
                    colorFilter = ColorFilter.tint(MaterialTheme.colors.primary)
                )
            } else {
                Image(
                    bitmap = ImageBitmap.imageResource(
                        LocalContext.current.resources,
                        R.drawable.play
                    ),
                    contentDescription = "",
                    modifier = Modifier
                        .size(60.dp)
                        .clickable {
                            playClickAction.invoke()
                        },
                    colorFilter = ColorFilter.tint(MaterialTheme.colors.primary)
                )
            }

            Image(
                bitmap = ImageBitmap.imageResource(
                    LocalContext.current.resources,
                    R.drawable.fastforward
                ),
                contentDescription = "",
                modifier = Modifier
                    .size(40.dp),
                colorFilter = ColorFilter.tint(MaterialTheme.colors.primary)
            )
        }
    }
}