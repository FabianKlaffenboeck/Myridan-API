package com.dynaHowl.myridan.routes

import com.dynaHowl.myridan.model.Part
import com.dynaHowl.myridan.services.PartService
import io.ktor.http.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.partRoute(partService: PartService) {
    route("/parts") {
        get {
            call.respond(partService.getAll())
        }
        get("{id}") {
            val id: Int = (call.parameters["id"] ?: return@get call.respond(HttpStatusCode.BadRequest)).toInt()
            val part = partService.getById(id) ?: return@get call.respond(HttpStatusCode.NotFound)
            call.respond(part)
        }
        post {
            val part = call.receive<Part>()
            if (part.id == null) {
                return@post call.respond(
                    partService.add(part)
                )
            }
            call.respond(partService.update(part))
        }
        delete("{id}") {
            val id: Int = (call.parameters["id"] ?: return@delete call.respond(HttpStatusCode.BadRequest)).toInt()
            call.respond(partService.delete(id))
        }
    }
}