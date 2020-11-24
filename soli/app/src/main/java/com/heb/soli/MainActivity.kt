package com.heb.soli

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.heb.soli.RadioStreamsAdapter.ItemCallback
import com.heb.soli.api.Media
import com.heb.soli.api.MediaId
import com.heb.soli.media.MediaRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    private val TAG = MainActivity::class.simpleName

    private lateinit var mediaRepository: MediaRepository
    private lateinit var radioStreamAdapter: RadioStreamsAdapter

    private lateinit var radioListView: RecyclerView
    private lateinit var playButton : ImageView
    private lateinit var mediaNameView : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        setSupportActionBar(findViewById(R.id.toolbar))

        mediaRepository = (application as SoliApp).appContainer.mediaRepository

        radioListView = findViewById(R.id.radio_stream_list)
        playButton = findViewById(R.id.play_button)
        mediaNameView = findViewById(R.id.current_media_title)

        radioStreamAdapter = RadioStreamsAdapter(object : ItemCallback {
            override fun onClicked(media: Media) {
                startService(PlayerService.buildPlayIntent(baseContext, media))
            }
        })
        radioListView.adapter = radioStreamAdapter
        radioListView.layoutManager = GridLayoutManager(this, 2)

        fetchAllRadios()

        playButton.setOnClickListener {
            val intent = PlayerService.buildCommandIntent(baseContext, ARG_ACTION_PLAY_PAUSE)
            startService(intent)
        }

        mediaNameView.setOnClickListener {
            val intent = Intent(applicationContext, PlayerActivity::class.java)
            startActivity(intent)
        }

        PlayerService.playerContext.observe(this, Observer {playerContext ->
            playerContext.mediaId?.let {
                val media = mediaRepository.getMedia(MediaId(it))
                mediaNameView.text = media?.name
            }

            if (playerContext.isPlaying) {
                playButton.setImageResource(R.drawable.exo_icon_pause)
            } else {
                playButton.setImageResource(R.drawable.exo_icon_play)
            }
        })
    }

    private fun fetchAllRadios() {
        Log.d(TAG, "fetch all radios")

        CoroutineScope(Dispatchers.IO).launch {
            mediaRepository.fetchRadioList()
            val radios = mediaRepository.getRadioList()
            Log.d(TAG, "${radios.size} radios found")
            withContext(Dispatchers.Main) {
                radioStreamAdapter.setItems(radios)
            }
        }
    }
}
