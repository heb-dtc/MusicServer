package com.heb.soli

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import com.heb.soli.media.MediaRepository
import com.heb.soli.ui.theme.SoliTheme

class MainActivity : AppCompatActivity() {

    private val TAG = MainActivity::class.simpleName

    private lateinit var mediaRepository: MediaRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            SoliTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Soli(homeViewModel = HomeViewModel((application as SoliApp).appContainer.mediaRepository))
                }
            }
        }

        mediaRepository = (application as SoliApp).appContainer.mediaRepository

        /*mediaNameView.setOnClickListener {
            val intent = Intent(applicationContext, PlayerActivity::class.java)
            startActivity(intent)
        }*/
    }
}