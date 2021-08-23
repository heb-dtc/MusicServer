package com.heb.soli.player

import android.content.Context
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.SimpleExoPlayer

class Player(context: Context) {

    private val exoPlayer: ExoPlayer = SimpleExoPlayer.Builder(context).build().apply {
        playWhenReady = true
    }

    fun play(uri: String) {
        val source: MediaItem = MediaItem.fromUri(uri)

        exoPlayer.setMediaItem(source)
        exoPlayer.prepare()
        exoPlayer.play()
    }

    fun isPlaying() = exoPlayer.isPlaying

    fun resume() {
        exoPlayer.play()
    }

    fun pause() {
        exoPlayer.pause()
    }

    fun release() {
        exoPlayer.release()
    }
}
