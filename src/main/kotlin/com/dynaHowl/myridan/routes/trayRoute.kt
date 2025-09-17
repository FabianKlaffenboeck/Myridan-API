package com.dynaHowl.myridan.routes

import com.dynaHowl.myridan.model.Tray
import com.dynaHowl.myridan.services.TrayService
import io.ktor.http.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.trayRoute(trayService: TrayService) {
    route("/trays") {
        get {
            call.respond(trayService.getAll())
        }
        get("/empty") {
            call.respond(trayService.getAllEmpty())
        }
        get("{id}") {
            val id: Int = (call.parameters["id"] ?: return@get call.respond(HttpStatusCode.BadRequest)).toInt()
            val tray = trayService.getById(id) ?: return@get call.respond(HttpStatusCode.NotFound)
            call.respond(tray)
        }
        post {
            val tray = call.receive<Tray>()
            if (tray.id == null) {
                return@post call.respond(
                    trayService.add(tray)
                )
            }
            call.respond(trayService.update(tray))
        }
        delete("{id}") {
            val id: Int = (call.parameters["id"] ?: return@delete call.respond(HttpStatusCode.BadRequest)).toInt()
            call.respond(trayService.delete(id))
        }
    }
}