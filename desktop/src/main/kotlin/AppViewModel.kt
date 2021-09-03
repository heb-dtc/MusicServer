import com.heb.soli.MediaRepository
import com.heb.soli.api.RadioStream
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

data class AppState(val radios: List<RadioStream>)

class AppViewModel(private val mediaRepository: MediaRepository) {

    private val _state = MutableStateFlow(AppState(emptyList()))
    private val podcastFeedUris = listOf(
        "https://www.afterhate.fr/feed/podcast",
        "https://feeds.soundcloud.com/users/soundcloud:users:274829367/sounds.rss",
        "https://feeds.audiomeans.fr/feed/8ca7aac1-479f-471c-b3a4-8b487e552bff.xml"
    )

    val state: MutableStateFlow<AppState> get() = _state

    init {
        GlobalScope.launch {
            mediaRepository.getRadioList().collect {
                withContext(Dispatchers.Main) {
                    _state.value = AppState(it)
                }
            }
            //val podcast = networkClient.fetchPodcastFeed(podcastFeedUris)
        }

    }
}