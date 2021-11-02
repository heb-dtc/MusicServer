package net.hebus.server.http.api

import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import mu.KotlinLogging
import net.hebus.repository.HistoryRepository
import net.hebus.repository.RadioRepository
import com.heb.soli.api.Media

private val logger = KotlinLogging.logger {}

fun Route.medias(radioRepository: RadioRepository, historyRepository: HistoryRepository) {

    get("/radios") {
        logger.debug { "GET /radios" }
        val radios = radioRepository.getRadios()
        call.respond(radios)
    }

    get("/history") {
        logger.debug { "GET /api/history" }
        val history = historyRepository.getPlayedHistory()
        call.respond(history)
    }

    post("/history") {
        logger.debug { "POST /api/history/"}
        val media = call.receive<Media>()
        historyRepository.addPlayedMedia(media)
        call.respond(HttpStatusCode.Accepted)
    }
}