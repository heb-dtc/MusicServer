package net.hebus.server.http

import io.ktor.application.call
import io.ktor.application.install
import io.ktor.features.ContentNegotiation
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.content.TextContent
import io.ktor.response.respond
import io.ktor.response.respondText
import io.ktor.routing.get
import io.ktor.routing.put
import io.ktor.routing.routing
import io.ktor.serialization.DefaultJsonConfiguration
import io.ktor.serialization.serialization
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import net.hebus.player.PlayerController
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonConfiguration
import net.hebus.player.PlayerStatus


class HttpServer(private val playerController: PlayerController,
                 private val json:Json = Json(JsonConfiguration.Stable)) {

    private val server = embeddedServer(Netty, port = 7700) {
        install(ContentNegotiation) {
            serialization(
                contentType = ContentType.Application.Json,
                json = Json (
                    DefaultJsonConfiguration.copy(
                        prettyPrint = true
                    )
                )
            )
        }
        routing {
            get("/") {
                call.respondText("Music Server Running", ContentType.Text.Plain)
            }
            get("/player") {
                val status = playerController.getPlayerStatus()
                call.respond(TextContent(json.stringify(PlayerStatus.serializer(), status),
                    ContentType.Application.Json))
            }
            put("/player/play") {
                playerController.play()
                call.respond(HttpStatusCode.Accepted)
            }
            put("/player/pause") {
                playerController.pause()
                call.respond(HttpStatusCode.Accepted)
            }
            put("/player/stop") {
                playerController.stop()
                call.respond(HttpStatusCode.Accepted)
            }
        }
    }

    fun start() {
        server.start(wait = true)
    }
}
