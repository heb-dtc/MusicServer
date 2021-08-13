package com.heb.soli

import android.app.*
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.lifecycle.LifecycleService
import androidx.lifecycle.lifecycleScope
import com.heb.soli.api.*
import com.heb.soli.media.MediaRepository
import com.heb.soli.player.Player
import com.heb.soli.player.PlayerContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

private const val NOTIFICATION_FOREGROUND_ID = 1
private const val PLAYER_SERVICE_CHANNEL_ID = "player-service-channel"
private const val ARG_ACTION_PLAY = "com.heb.play"
private const val ARG_RADIO_ID = "radio_id"
private const val ARG_PODCAST_TITLE = "podcast_title"
private const val ARG_PODCAST_EPISODE = "podcast_episode"
private const val ARG_MEDIA_TYPE = "media_id"
const val ARG_ACTION_PLAY_PAUSE = "com.heb.playpause"

class PlayerService : LifecycleService() {

    private lateinit var player: Player
    private lateinit var mediaRepository: MediaRepository

    companion object {
        val TAG: String = PlayerService::class.java.simpleName

        val playerContext =
            MutableStateFlow(PlayerContext(media = NO_MEDIA, isPlaying = false))

        fun buildPlayRadioIntent(context: Context, radio: Media) =
            Intent(context, PlayerService::class.java).apply {
                putExtra(ARG_RADIO_ID, radio.id.id)
                putExtra(ARG_MEDIA_TYPE, MediaType.RADIO_STREAM.toString())
                action = ARG_ACTION_PLAY
            }

        fun buildPlayPodcastIntent(context: Context, feedTitle: String, episodeIndex: Int) =
            Intent(context, PlayerService::class.java).apply {
                putExtra(ARG_PODCAST_TITLE, feedTitle)
                putExtra(ARG_PODCAST_EPISODE, episodeIndex)
                putExtra(ARG_MEDIA_TYPE, MediaType.PODCAST_EPISODE.toString())
                action = ARG_ACTION_PLAY
            }

        fun buildCommandIntent(context: Context, command: String) =
            Intent(context, PlayerService::class.java).apply {
                action = command
            }
    }

    override fun onCreate() {
        super.onCreate()
        player = Player(applicationContext)
        mediaRepository = (application as SoliApp).appContainer.mediaRepository
    }

    private fun play(mediaUri: String) {
        player.play(mediaUri)
    }

    private fun buildNotification(): Notification {
        val intent = Intent(applicationContext, MainActivity::class.java)
        val contentIntent = PendingIntent.getActivity(this, 0, intent, 0)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannel(
                applicationContext,
                PLAYER_SERVICE_CHANNEL_ID,
                "SoLi music playback service"
            )
        }

        return NotificationCompat.Builder(this, PLAYER_SERVICE_CHANNEL_ID)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(contentIntent)
            .setCategory(Notification.CATEGORY_SERVICE)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("Soli Player")
            .setContentText("playing something")
            .build()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun createNotificationChannel(
        context: Context,
        channelId: String,
        channelName: String
    ): String {
        val channel =
            NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_LOW)
        channel.lightColor = Color.BLUE
        channel.lockscreenVisibility = Notification.VISIBILITY_PRIVATE
        val service = context.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        service.createNotificationChannel(channel)
        return channelId
    }

    override fun onBind(intent: Intent): IBinder? {
        super.onBind(intent)
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        super.onStartCommand(intent, flags, startId)

        intent?.let {
            when (it.action) {
                ARG_ACTION_PLAY -> {
                    startForeground(NOTIFICATION_FOREGROUND_ID, buildNotification())

                    val type = MediaType.valueOf(
                        intent.getStringExtra(ARG_MEDIA_TYPE) ?: MediaType.NO_MEDIA.name
                    )

                    val media = when (type) {
                        MediaType.RADIO_STREAM -> {
                            val id = intent.getIntExtra(ARG_RADIO_ID, -1)
                            mediaRepository.getRadio(MediaId(id)) ?: NO_MEDIA
                        }
                        MediaType.PODCAST_EPISODE -> {
                            val title = intent.getStringExtra(ARG_PODCAST_TITLE)
                            val episodeIndex = intent.getIntExtra(ARG_PODCAST_EPISODE, -1)

                            title?.let {
                                val podcastFeed = mediaRepository.getPodcastFeed(title)
                                podcastFeed?.let { feed ->
                                    feed.episodes[episodeIndex].toMedia()
                                } ?: NO_MEDIA
                            } ?: NO_MEDIA
                        }
                        else -> {
                            NO_MEDIA
                        }
                    }

                    if (media != NO_MEDIA) {
                        play(media.url)
                    }

                    lifecycleScope.launch {
                        playerContext.emit(PlayerContext(media, true))
                    }
                }
                ARG_ACTION_PLAY_PAUSE -> {
                    if (player.isPlaying()) {
                        stopForeground(false)
                        player.pause()

                        lifecycleScope.launch {
                            playerContext.emit(PlayerContext(playerContext.value.media, false))
                        }
                    } else {
                        startForeground(NOTIFICATION_FOREGROUND_ID, buildNotification())
                        player.resume()

                        lifecycleScope.launch {
                            playerContext.emit(PlayerContext(playerContext.value.media, true))
                        }
                    }
                }
                else -> Log.e(TAG, "unknown action")
            }
        }

        return START_STICKY
    }

    override fun onDestroy() {
        player.release()
        stopForeground(true)
        super.onDestroy()
    }
}
