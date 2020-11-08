package com.heb.soli

import android.app.*
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.IBinder
import androidx.annotation.MainThread
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.heb.soli.api.Media
import com.heb.soli.player.Player

private const val NOTIFICATION_FOREGROUND_ID = 1
private const val PLAYER_SERVICE_CHANNEL_ID = "player-service-channel"
private const val ARG_MEDIA_URI = "media_url"

class PlayerService : Service() {

    private lateinit var player: Player

    companion object {
        @MainThread
        fun buildIntent(context: Context, media: Media) =
            Intent(context, PlayerService::class.java).apply {
                putExtra(ARG_MEDIA_URI, media.url)
            }
    }

    override fun onCreate() {
        super.onCreate()
        player = Player(applicationContext)
    }

    @MainThread
    private fun handleIntent(intent: Intent) {
        val mediaUri = intent.getStringExtra(ARG_MEDIA_URI)

        mediaUri?.let {
            play(it)
        }
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
        return null
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        handleIntent(intent)
        startForeground(NOTIFICATION_FOREGROUND_ID, buildNotification())
        return START_STICKY
    }

    override fun onDestroy() {
        player.release()
        super.onDestroy()
    }
}
