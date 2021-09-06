import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jetbrains.skija.Image
import java.io.ByteArrayOutputStream
import java.net.HttpURLConnection
import java.net.URL
import javax.imageio.ImageIO

@Composable
fun NetworkImage(imageUri: String, modifier: Modifier) {
    var imageBitmap = remember { mutableStateOf<ImageBitmap?>(null) }

    LaunchedEffect(imageUri) {
        println("launched for $imageUri")
        withContext(Dispatchers.IO) {
            val url = URL(imageUri)
            val connection = url.openConnection() as HttpURLConnection
            connection.connect()

            val inputStream = connection.inputStream
            val bufferedImage = ImageIO.read(inputStream)

            if (bufferedImage != null) {
                val stream = ByteArrayOutputStream()
                ImageIO.write(bufferedImage, "png", stream)
                val byteArray = stream.toByteArray()

                imageBitmap.value = Image.makeFromEncoded(byteArray).asImageBitmap()
            }
        }
    }

    if (imageBitmap.value != null) {
        Image(
            bitmap = imageBitmap.value!!,
            modifier = modifier,
            contentDescription = ""
        )
    } else {
        Box(
            modifier = modifier
        )
    }
}