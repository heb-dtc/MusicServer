package com.heb.soli

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.Observer

class PlayerActivity : AppCompatActivity() {

    private lateinit var playButton : Button
    private lateinit var mediaNameView : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player)
        playButton = findViewById(R.id.play_button)
        mediaNameView = findViewById(R.id.media_name)

        playButton.setOnClickListener {
            val intent = PlayerService.buildCommandIntent(baseContext, ARG_ACTION_PLAY_PAUSE)
            startService(intent)
        }

        PlayerService.playerContext.observe(this, Observer {playerContext ->
            playerContext.mediaUri?.let {
                mediaNameView.text = it
            }
            playButton.text = if (playerContext.isPlaying) "pause" else "play"
        })
    }
}
