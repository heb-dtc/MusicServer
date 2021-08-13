package com.heb.soli.player

import com.heb.soli.api.MediaId

data class PlayerContext(val mediaId: MediaId, val isPlaying: Boolean)
