package net.hebus.server.http

import io.ktor.application.*
import io.ktor.features.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.serialization.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import kotlinx.serialization.json.Json
import mu.KotlinLogging
import net.hebus.repository.HistoryRepository
import net.hebus.repository.RadioRepository
import net.hebus.server.http.api.apiRoute
import net.hebus.service.MusicService
import java.net.URI

private val logger = KotlinLogging.logger {}

class HttpServer(
    port: Int,
    private val musicService: MusicService,
    private val radioRepository: RadioRepository,
    private val historyRepository: HistoryRepository
) {

    private val server = embeddedServer(Netty, port = port) {
        install(ContentNegotiation) {
            json(
                contentType = ContentType.Application.Json,
                json = Json {}
            )
        }
        routing {
            /*get("/") {
                call.respondHtml {
                    head {
                        title { +"MSRV" }
                    }
                    body {
                        h1 {
                            +"MSRV"
                        }
                        p {
                            val track = musicService.getPlayerStatus().track
                            +"current track: $track"
                        }
                    }
                }
            }*/
            apiRoute(radioRepository, historyRepository)
            get("/player") {
                val status = musicService.getPlayerStatus()
                call.respond(status)
            }
            get("/player/play") {
                musicService.play()
                call.respond(HttpStatusCode.OK)
            }
            get("/player/pause") {
                musicService.pause()
                call.respond(HttpStatusCode.OK)
            }
            get("/player/stop") {
                musicService.stop()
                call.respond(HttpStatusCode.OK)
            }
            post("/player/load") {
                val audioStreamUrl = call.receive<AudioStreamUrl>()
                logger.debug { "receive POST /load" }
                musicService.load(URI.create(audioStreamUrl.data))
                call.respond(HttpStatusCode.OK)
            }
            get("/player/start") {
                musicService.load("/home/flo/SBTRKT_EM.mp3")
                call.respond(HttpStatusCode.OK)
            }
        }
    }

    fun start() {
        server.start(wait = true)
    }
}
