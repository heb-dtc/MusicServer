package net.hebus.player

import kotlinx.serialization.Serializable

@Serializable
data class PlayerStatus(val isPlaying: Boolean, var track: String?)
