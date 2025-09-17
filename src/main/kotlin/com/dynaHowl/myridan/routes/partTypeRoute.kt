package com.dynaHowl.myridan.routes

import com.dynaHowl.myridan.model.PartType
import com.dynaHowl.myridan.services.PartTypeService
import io.ktor.http.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.partTypeRoute(partTypeService: PartTypeService) {
    route("/partTypes") {
        get {
            call.respond(partTypeService.getAll())
        }
        get("{id}") {
            val id: Int = (call.parameters["id"] ?: return@get call.respond(HttpStatusCode.BadRequest)).toInt()
            val partType = partTypeService.getById(id) ?: return@get call.respond(HttpStatusCode.NotFound)
            call.respond(partType)
        }
        post {
            val partType = call.receive<PartType>()
            if (partType.id == null) {
                return@post call.respond(
                    partTypeService.add(partType)
                )
            }
            call.respond(partTypeService.update(partType))
        }
        delete("{id}") {
            val id: Int = (call.parameters["id"] ?: return@delete call.respond(HttpStatusCode.BadRequest)).toInt()
            call.respond(partTypeService.delete(id))
        }
    }
}