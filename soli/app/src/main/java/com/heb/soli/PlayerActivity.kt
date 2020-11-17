package com.heb.soli

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class PlayerActivity : AppCompatActivity() {

    private lateinit var playButton : View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player)
        playButton = findViewById(R.id.play_button)

        playButton.setOnClickListener {
            val intent = PlayerService.buildCommandIntent(baseContext, ARG_ACTION_PLAY_PAUSE)
            startService(intent)
        }
    }
}
