package net.hebus.server.http

import com.fasterxml.jackson.databind.SerializationFeature
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.features.ContentNegotiation
import io.ktor.html.respondHtml
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.content.TextContent
import io.ktor.jackson.jackson
import io.ktor.request.receive
import io.ktor.response.respond
import io.ktor.response.respondText
import io.ktor.routing.get
import io.ktor.routing.post
import io.ktor.routing.put
import io.ktor.routing.routing
import io.ktor.serialization.DefaultJsonConfiguration
import io.ktor.serialization.serialization
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonConfiguration
import kotlinx.html.*
import mu.KotlinLogging
import net.hebus.player.PlayerStatus
import net.hebus.service.MusicService
import java.lang.Compiler.enable
import java.net.URI

private val logger = KotlinLogging.logger {}

class HttpServer(private val musicService: MusicService, private val json: Json = Json(JsonConfiguration.Stable)) {

    private val server = embeddedServer(Netty, port = 8080) {
        install(ContentNegotiation) {
            jackson {
                enable(SerializationFeature.INDENT_OUTPUT)
            }
        }
        routing {
            get("/") {
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
                          + "current track: $track"
                        }
                    }
                }
            }
            get("/player") {
                val status = musicService.getPlayerStatus()
                call.respond(
                    TextContent(
                        json.stringify(PlayerStatus.serializer(), status),
                        ContentType.Application.Json
                    )
                )
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
