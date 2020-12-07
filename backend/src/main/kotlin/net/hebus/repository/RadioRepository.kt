package net.hebus.repository

import net.hebus.server.http.Media
import net.hebus.server.http.MediaId
import net.hebus.server.http.MediaType

class RadioRepository {

    fun getRadios() : List<Media> {
        return listOf(
            Media(MediaId(1), "soma", "https://somafm.com/groovesalad256.pls", MediaType.RADIO_STREAM),
            Media(MediaId(2), "france info", "https://stream.radiofrance.fr/franceinfo/franceinfo.m3u8", MediaType.RADIO_STREAM),
            Media(MediaId(3), "france inter", "https://stream.radiofrance.fr/franceinter/franceinter.m3u8", MediaType.RADIO_STREAM),
            Media(MediaId(4), "france musique", "https://stream.radiofrance.fr/francemusique/francemusique.m3u8", MediaType.RADIO_STREAM),
            Media(MediaId(5), "fip", "https://stream.radiofrance.fr/fip/fip.m3u8", MediaType.RADIO_STREAM),
            Media(MediaId(6), "nova", "http://broadcast.infomaniak.net/radionova-high.mp3", MediaType.RADIO_STREAM)
        )
    }
}