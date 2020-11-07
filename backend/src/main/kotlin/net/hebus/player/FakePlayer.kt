package net.hebus.player

import java.net.URI

class FakePlayer : Player {
    override fun load(filePath: String) {
    }

    override fun load(uri: URI) {
    }

    override fun play() {
    }

    override fun pause() {
    }

    override fun stop() {
    }

    override fun getStatus(): PlayerStatus {
        return PlayerStatus(false, null)
    }
}