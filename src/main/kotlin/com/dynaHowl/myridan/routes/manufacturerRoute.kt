package com.dynaHowl.myridan.routes

import com.dynaHowl.myridan.model.Manufacturer
import com.dynaHowl.myridan.services.ManufacturerService
import io.ktor.http.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.manufacturerRoute(manufacturerService: ManufacturerService) {
    route("/manufacturers") {
        get {
            call.respond(manufacturerService.getAll())
        }
        get("{id}") {
            val id: Int = (call.parameters["id"] ?: return@get call.respond(HttpStatusCode.BadRequest)).toInt()
            val manufacturer = manufacturerService.getById(id) ?: return@get call.respond(HttpStatusCode.NotFound)
            call.respond(manufacturer)
        }
        post {
            val manufacturer = call.receive<Manufacturer>()
            if (manufacturer.id == null) {
                return@post call.respond(
                    manufacturerService.add(manufacturer)
                )
            }
            call.respond(manufacturerService.update(manufacturer))
        }
        delete("{id}") {
            val id: Int = (call.parameters["id"] ?: return@delete call.respond(HttpStatusCode.BadRequest)).toInt()
            call.respond(manufacturerService.delete(id))
        }
    }
}