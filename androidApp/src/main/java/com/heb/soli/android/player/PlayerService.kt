package com.heb.soli.android.player

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.graphics.BitmapFactory
import android.graphics.Color
import android.media.MediaMetadata
import android.os.Build
import android.os.IBinder
import android.support.v4.media.MediaMetadataCompat
import android.support.v4.media.session.MediaSessionCompat
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.lifecycle.LifecycleService
import androidx.lifecycle.lifecycleScope
import com.heb.soli.android.MainActivity
import com.heb.soli.android.R
import com.heb.soli.android.SoliApp
import com.heb.soli.android.api.Media
import com.heb.soli.android.api.MediaId
import com.heb.soli.android.api.MediaType
import com.heb.soli.android.api.NO_MEDIA
import com.heb.soli.android.media.MediaRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

private const val NOTIFICATION_FOREGROUND_ID = 1
private const val PLAYER_SERVICE_CHANNEL_ID = "player-service-channel"
private const val ARG_ACTION_PLAY = "com.heb.play"

private const val ARG_MEDIA_ID = "media_id"
private const val ARG_MEDIA_TYPE = "media_type"
private const val ARG_MEDIA_URI = "media_uri"
private const val ARG_MEDIA_NAME = "media_name"

const val ARG_ACTION_PLAY_PAUSE = "com.heb.playpause"

class PlayerService : LifecycleService() {

    private lateinit var player: Player
    private lateinit var mediaRepository: MediaRepository

    private lateinit var mediaSession: MediaSessionCompat

    companion object {
        val TAG: String = PlayerService::class.java.simpleName

        val playerContext =
            MutableStateFlow(PlayerContext(media = NO_MEDIA, isPlaying = false))

        fun buildCommandIntent(context: Context, command: String) =
            Intent(context, PlayerService::class.java).apply {
                action = command
            }

        fun buildPlayMediaIntent(context: Context, media: Media) =
            Intent(context, PlayerService::class.java).apply {
                putExtra(ARG_MEDIA_ID, media.id.id)
                putExtra(ARG_MEDIA_TYPE, media.type.toString())
                putExtra(ARG_MEDIA_URI, media.url)
                putExtra(ARG_MEDIA_NAME, media.name)
                action = ARG_ACTION_PLAY
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

    private fun buildNotification(media: Media, isPlaying: Boolean): Notification {
        val intent = Intent(applicationContext, MainActivity::class.java)
        val contentIntent = PendingIntent.getActivity(this, 0, intent, 0)
        val playPauseIntent = buildCommandIntent(baseContext, ARG_ACTION_PLAY_PAUSE)
        val pendingPlayPauseIntent = PendingIntent.getService(this, 0, playPauseIntent, 0)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannel(
                applicationContext,
                PLAYER_SERVICE_CHANNEL_ID,
                "SoLi music playback service"
            )
        }

        mediaSession = MediaSessionCompat(this, "PlayerService")
        mediaSession.setMetadata(
            MediaMetadataCompat.Builder()
                .putString(MediaMetadata.METADATA_KEY_TITLE, media.name)
                //.putString(MediaMetadata.METADATA_KEY_ARTIST, )
                .putBitmap(
                    MediaMetadata.METADATA_KEY_ALBUM_ART, BitmapFactory.decodeResource(
                        Resources.getSystem(), R.drawable.img_placeholder
                    )
                )
                //.putLong(MediaMetadata.METADATA_KEY_DURATION, )
                .build()
        )

        val mediaStyle = androidx.media.app.NotificationCompat.MediaStyle()
            .setMediaSession(mediaSession.sessionToken)

        val action = if (isPlaying) {
            NotificationCompat.Action.Builder(
                android.R.drawable.ic_media_pause, "Pause", pendingPlayPauseIntent
            ).build()
        } else {
            NotificationCompat.Action.Builder(
                android.R.drawable.ic_media_play, "Play", pendingPlayPauseIntent
            ).build()
        }

        return NotificationCompat.Builder(this, PLAYER_SERVICE_CHANNEL_ID)
            .setStyle(mediaStyle)
            .setContentIntent(contentIntent)
            .setSmallIcon(R.drawable.img_placeholder)
            .addAction(action)
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
                    val type = MediaType.valueOf(
                        intent.getStringExtra(ARG_MEDIA_TYPE) ?: MediaType.NO_MEDIA.name
                    )
                    val id = intent.getStringExtra(ARG_MEDIA_ID) ?: ""
                    val uri = intent.getStringExtra(ARG_MEDIA_URI) ?: ""
                    val name = intent.getStringExtra(ARG_MEDIA_NAME) ?: ""
                    val media = Media(id = MediaId(id), name = name, url = uri, type = type)

                    startForeground(
                        NOTIFICATION_FOREGROUND_ID,
                        buildNotification(media = media, isPlaying = true)
                    )

                    if (media != NO_MEDIA) {
                        Log.d(TAG, "Attempting to play media with uri ${media.url}")
                        play(media.url)
                    }

                    lifecycleScope.launch {
                        playerContext.emit(PlayerContext(media, true))
                    }
                }

                ARG_ACTION_PLAY_PAUSE -> {
                    if (player.isPlaying()) {
                        //stopForeground(false)
                        startForeground(
                            NOTIFICATION_FOREGROUND_ID,
                            buildNotification(playerContext.value.media, false)
                        )
                        player.pause()

                        lifecycleScope.launch {
                            playerContext.emit(PlayerContext(playerContext.value.media, false))
                        }
                    } else {
                        startForeground(
                            NOTIFICATION_FOREGROUND_ID,
                            buildNotification(playerContext.value.media, true)
                        )
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
