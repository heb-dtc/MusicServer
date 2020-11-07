package com.heb.soli

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.heb.soli.api.NetworkClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {

    private val TAG = MainActivity::class.simpleName

    private val networkClient = NetworkClient()
    private val radioStreamAdapter = RadioStreamsAdapter()

    private lateinit var radioListView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        radioListView = findViewById(R.id.radio_stream_list)
        radioListView.adapter = radioStreamAdapter
        radioListView.layoutManager = GridLayoutManager(this, 2)

        fetchAllRadios()
    }

    private fun fetchAllRadios() {
        Log.d(TAG, "fetch all radios")
        CoroutineScope(Dispatchers.IO).launch {
            val radios = networkClient.fetchAllRadios()
            Log.d(TAG, "${radios.size} radios found")
            withContext(Dispatchers.Main) {
                radioStreamAdapter.setItems(radios)
            }
        }
    }
}
