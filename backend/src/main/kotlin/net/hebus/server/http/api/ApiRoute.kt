package net.hebus.server.http.api

import io.ktor.routing.*
import net.hebus.repository.HistoryRepository
import net.hebus.repository.RadioRepository

fun Routing.apiRoute(radioRepository: RadioRepository, historyRepository: HistoryRepository) {
    route("/api") {
        medias(radioRepository, historyRepository)
    }
}