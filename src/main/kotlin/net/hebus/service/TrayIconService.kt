package net.hebus.service

import dorkbox.systemTray.Menu
import dorkbox.systemTray.MenuItem
import mu.KotlinLogging
import dorkbox.systemTray.SystemTray
import java.io.IOException
import java.lang.Thread.currentThread
import java.net.URI


private val logger = KotlinLogging.logger {}

class TrayIconService(private val systemTray: SystemTray, private val musicService: MusicService) : MusicServiceListener {

    fun loadTrayIcon() {
        try {
            val url = Thread::currentThread.javaClass.classLoader.getResource("icon.png")
            systemTray.setImage(url)
        } catch (e: IOException) {
            logger.error { "can´t add tray icon -> ${e.message}" }
        }

        systemTray.status = "not playing";

        val playerControlMenu = Menu("player")
        playerControlMenu.add(MenuItem("play"){
            logger.debug { "play" }
            musicService.play()
        })
        playerControlMenu.add(MenuItem("pause"){
            logger.debug { "pause" }
            musicService.pause()
        })
        playerControlMenu.add(MenuItem("stop"){
            logger.debug { "stop" }
            musicService.stop()
        })

        val radioMenu = Menu("radio")
        radioMenu.add(MenuItem("somaFM"){
            logger.debug { "starting somaFM" }
            musicService.load(URI.create("http://ice2.somafm.com/groovesalad-256-mp3"))
        })
        radioMenu.add(MenuItem("francinfo"){
            logger.debug { "starting france info" }
            musicService.load(URI.create("http://direct.franceinfo.fr/live/franceinfo-lofi.mp3"))
        })
        radioMenu.add(MenuItem("nova"){
            logger.debug { "starting nova" }
            musicService.load(URI.create("http://broadcast.infomaniak.net/radionova-high.mp3"))
        })

        systemTray.menu.add(playerControlMenu)
        systemTray.menu.add(radioMenu)

        systemTray.menu.setCallback {
            logger.debug { "menu callback" }
            val isPlaying: Boolean = musicService.getPlayerStatus().isPlaying
            systemTray.status =  if (isPlaying) {
                "playing"
            } else {
                "not playing"
            }
        }
    }

    override fun playerStateUpdate(state: PlayerState) {
        when (state) {
           PlayerState.PLAYING -> systemTray.status = "playing"
           PlayerState.STOPPED -> systemTray.status = "stopped"
           PlayerState.PAUSED -> systemTray.status = "paused"
        }
    }
}
