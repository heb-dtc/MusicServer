package net.hebus.player

class PlayerController(private val player: Player) {

    fun getPlayerStatus() = player.getStatus()

    fun play() = player.play()

    fun pause() = player.pause()

    fun stop() = player.stop()
}
