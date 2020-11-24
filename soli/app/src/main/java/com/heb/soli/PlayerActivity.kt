package com.heb.soli

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.Observer
import com.heb.soli.api.MediaId
import com.heb.soli.media.MediaRepository

class PlayerActivity : AppCompatActivity() {

    private lateinit var playButton : Button
    private lateinit var mediaNameView : TextView
    private lateinit var mediaRepository: MediaRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_player)
        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        mediaRepository = (application as SoliApp).appContainer.mediaRepository

        playButton = findViewById(R.id.play_button)
        mediaNameView = findViewById(R.id.media_name)

        playButton.setOnClickListener {
            val intent = PlayerService.buildCommandIntent(baseContext, ARG_ACTION_PLAY_PAUSE)
            startService(intent)
        }

        PlayerService.playerContext.observe(this, Observer {playerContext ->
            playerContext.mediaId?.let {
                val media = mediaRepository.getMedia(MediaId(it))
                mediaNameView.text = media?.name
            }
            playButton.text = if (playerContext.isPlaying) "pause" else "play"
        })
    }
}
