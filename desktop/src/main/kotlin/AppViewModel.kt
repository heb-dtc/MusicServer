import com.heb.soli.MediaRepository
import com.heb.soli.api.RadioStream
import com.heb.soli.api.PodcastFeed
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

data class AppState(val radios: List<RadioStream>, val podcastList: List<PodcastFeed>)

class AppViewModel(private val mediaRepository: MediaRepository) {

    private val _state = MutableStateFlow(AppState(emptyList(), emptyList()))

    val state: MutableStateFlow<AppState> get() = _state

    init {
        GlobalScope.launch {
            mediaRepository.getRadioList().combine(mediaRepository.getPodcasts()) { radios, podcast ->
                AppState(radios, podcast)
            }.collect {
                withContext(Dispatchers.Main) {
                    _state.value = it
                }
            }
        }

    }
}