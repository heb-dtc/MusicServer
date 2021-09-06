import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.jetbrains.skija.Image
import java.io.ByteArrayOutputStream
import java.net.HttpURLConnection
import java.net.URL
import javax.imageio.ImageIO

data class RemoteImageState(val loading: Boolean = true, val imageBitmap: ImageBitmap? = null)

class RemoteImageLoader(private val imageUri: String) {
    private var _state = MutableStateFlow(RemoteImageState())
    val state get() = _state.asStateFlow()

    init {
        GlobalScope.launch {
            val imageBitmap = loadNetworkImage(imageUri)
            _state.emit(RemoteImageState(loading = false, imageBitmap))
        }
    }

    private suspend fun loadNetworkImage(link: String): ImageBitmap? {
        println("loading image $link")
        var imageBitmap: ImageBitmap? = null

        withContext(Dispatchers.IO) {
            val url = URL(link)
            val connection = url.openConnection() as HttpURLConnection
            connection.connect()

            val inputStream = connection.inputStream
            val bufferedImage = ImageIO.read(inputStream)

            if (bufferedImage != null) {
                val stream = ByteArrayOutputStream()
                ImageIO.write(bufferedImage, "png", stream)
                val byteArray = stream.toByteArray()

                imageBitmap = Image.makeFromEncoded(byteArray).asImageBitmap()
            }
        }

        return imageBitmap
    }
}