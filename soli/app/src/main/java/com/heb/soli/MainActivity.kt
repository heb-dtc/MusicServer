package com.heb.soli

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.heb.soli.RadioStreamsAdapter.ItemCallback
import com.heb.soli.api.Media
import com.heb.soli.api.NetworkClient
import com.heb.soli.media.MediaRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    private val TAG = MainActivity::class.simpleName

    private lateinit var mediaRepository: MediaRepository
    private lateinit var radioStreamAdapter : RadioStreamsAdapter

    private lateinit var radioListView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        mediaRepository = (application as SoliApp).appContainer.mediaRepository

        radioListView = findViewById(R.id.radio_stream_list)
        radioStreamAdapter = RadioStreamsAdapter(object : ItemCallback {
            override fun onClicked(media: Media) {
                startService(PlayerService.buildPlayIntent(baseContext, media))
            }
        })
        radioListView.adapter = radioStreamAdapter
        radioListView.layoutManager = GridLayoutManager(this, 2)

        fetchAllRadios()
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
