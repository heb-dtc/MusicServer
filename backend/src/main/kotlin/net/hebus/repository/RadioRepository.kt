package net.hebus.repository

import net.hebus.server.http.MediaId
import net.hebus.server.http.RadioStream

class RadioRepository {

    fun getRadios() : List<RadioStream> {
        return listOf(
            RadioStream(MediaId("1"), "soma", "https://somafm.com/groovesalad256.pls"),
            RadioStream(MediaId("2"), "france info", "https://stream.radiofrance.fr/franceinfo/franceinfo.m3u8"),
            RadioStream(MediaId("3"), "france inter", "https://stream.radiofrance.fr/franceinter/franceinter.m3u8"),
            RadioStream(MediaId("4"), "france musique", "https://stream.radiofrance.fr/francemusique/francemusique.m3u8"),
            RadioStream(MediaId("5"), "fip", "https://stream.radiofrance.fr/fip/fip.m3u8"),
            RadioStream(MediaId("6"), "nova", "http://novazz.ice.infomaniak.ch/novazz-128.mp3")
        )
    }
}