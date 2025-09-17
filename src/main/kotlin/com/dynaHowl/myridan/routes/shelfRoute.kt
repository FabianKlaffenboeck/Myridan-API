package com.dynaHowl.myridan.routes

import com.dynaHowl.myridan.model.Shelf
import com.dynaHowl.myridan.services.ShelfService
import io.ktor.http.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.shelfRoute(shelfService: ShelfService) {
    route("/shelfs") {
        get {
            call.respond(shelfService.getAll())
        }
        get("{id}") {
            val id: Int = (call.parameters["id"] ?: return@get call.respond(HttpStatusCode.BadRequest)).toInt()
            val shelf = shelfService.getById(id) ?: return@get call.respond(HttpStatusCode.NotFound)
            call.respond(shelf)
        }
        post {
            val shelf = call.receive<Shelf>()
            if (shelf.id == null) {
                return@post call.respond(
                    shelfService.add(shelf)
                )
            }
            call.respond(shelfService.update(shelf))
        }
        delete("{id}") {
            val id: Int = (call.parameters["id"] ?: return@delete call.respond(HttpStatusCode.BadRequest)).toInt()
            call.respond(shelfService.delete(id))
        }
    }
}