import com.heb.soli.MediaRepository
import com.heb.soli.api.PodcastEpisode
import com.heb.soli.api.PodcastFeed
import com.heb.soli.api.RadioStream
import com.heb.soli.api.toMedia
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

data class AppState(
    val loading: Boolean,
    val radios: List<RadioStream>,
    val podcastList: List<PodcastFeed>,
    val episodes: List<PodcastEpisode>
)

class AppViewModel(private val mediaRepository: MediaRepository, private val player: Player) {

    private val _state =
        MutableStateFlow(
            AppState(
                loading = true,
                radios = emptyList(),
                podcastList = emptyList(),
                episodes = emptyList()
            )
        )

    val state: StateFlow<AppState> get() = _state

    init {
        GlobalScope.launch {
            mediaRepository.getRadioList()
                .combine(mediaRepository.getPodcasts()) { radios, podcast ->
                    AppState(loading = false, radios, podcast, emptyList())
                }.collect {
                    _state.emit(it)
                }
        }
    }

    fun playRadio(radio: RadioStream) {
        player.play(radio.toMedia())
    }

    fun playPodcastEpisode(episode: PodcastEpisode) {
        player.play(episode.toMedia())
    }

    fun loadPodcastFeed(episodes: List<PodcastEpisode>) {
        GlobalScope.launch {
            _state.emit(_state.value.copy(episodes = episodes))
        }
    }
}