package com.dynaHowl.myridan.routes

import com.dynaHowl.myridan.model.Footprint
import com.dynaHowl.myridan.services.FootprintService
import io.ktor.http.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.footprintRoute(footprintService: FootprintService) {
    route("/footprints") {
        get {
            call.respond(footprintService.getAll())
        }
        get("{id}") {
            val id: Int = (call.parameters["id"] ?: return@get call.respond(HttpStatusCode.BadRequest)).toInt()
            val footprint = footprintService.getById(id) ?: return@get call.respond(HttpStatusCode.NotFound)
            call.respond(footprint)
        }
        post {
            val footprint = call.receive<Footprint>()
            if (footprint.id == null) {
                return@post call.respond(
                    footprintService.add(footprint)
                )
            }
            call.respond(footprintService.update(footprint))
        }
        delete("{id}") {
            val id: Int = (call.parameters["id"] ?: return@delete call.respond(HttpStatusCode.BadRequest)).toInt()
            call.respond(footprintService.delete(id))
        }
    }
}