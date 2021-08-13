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
import com.heb.soli.api.Media
import com.heb.soli.api.MediaId
import com.heb.soli.api.NO_MEDIA_ID
import com.heb.soli.media.MediaRepository
import com.heb.soli.player.Player
import com.heb.soli.player.PlayerContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

private const val NOTIFICATION_FOREGROUND_ID = 1
private const val PLAYER_SERVICE_CHANNEL_ID = "player-service-channel"
private const val ARG_ACTION_PLAY = "com.heb.play"
private const val ARG_MEDIA_ID = "media_id"
const val ARG_ACTION_PLAY_PAUSE = "com.heb.playpause"

class PlayerService : LifecycleService() {

    private lateinit var player: Player
    private lateinit var mediaRepository: MediaRepository

    companion object {
        val TAG: String = PlayerService::class.java.simpleName

        val playerContext =
            MutableStateFlow(PlayerContext(mediaId = NO_MEDIA_ID, isPlaying = false))

        fun buildPlayIntent(context: Context, media: Media) =
            Intent(context, PlayerService::class.java).apply {
                putExtra(ARG_MEDIA_ID, media.id.id)
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
                    val id = intent.getIntExtra(ARG_MEDIA_ID, -1)
                    val media = mediaRepository.getMedia(MediaId(id))

                    media?.let { itMedia ->
                        play(itMedia.url)

                        lifecycleScope.launch {
                            playerContext.emit(PlayerContext(itMedia.id, true))
                        }
                    }
                }
                ARG_ACTION_PLAY_PAUSE -> {
                    if (player.isPlaying()) {
                        stopForeground(false)
                        player.pause()

                        lifecycleScope.launch {
                            playerContext.emit(PlayerContext(playerContext.value.mediaId, false))
                        }
                    } else {
                        startForeground(NOTIFICATION_FOREGROUND_ID, buildNotification())
                        player.resume()

                        lifecycleScope.launch {
                            playerContext.emit(PlayerContext(playerContext.value.mediaId, true))
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
