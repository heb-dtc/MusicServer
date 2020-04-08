package net.hebus.server.http

import io.ktor.application.call
import io.ktor.application.install
import io.ktor.features.ContentNegotiation
import io.ktor.html.respondHtml
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.content.TextContent
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
import net.hebus.player.PlayerStatus
import net.hebus.service.MusicService
import java.net.URI


class HttpServer(private val musicService: MusicService, private val json: Json = Json(JsonConfiguration.Stable)) {

    private val server = embeddedServer(Netty, port = 80) {
        install(ContentNegotiation) {
            serialization(
                contentType = ContentType.Application.Json,
                json = Json(
                    DefaultJsonConfiguration.copy(
                        prettyPrint = true
                    )
                )
            )
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
            post("player/load") {
                val args = call.receive<String>()
                musicService.load(URI.create(args))
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
