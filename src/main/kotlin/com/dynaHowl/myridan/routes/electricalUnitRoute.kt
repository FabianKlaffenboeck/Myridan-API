package com.dynaHowl.myridan.routes

import com.dynaHowl.myridan.model.ElectricalUnit
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.electricalUnitRoute() {
    route("/electricalUnits") {
        get {
            call.respond(ElectricalUnit.entries)
        }
    }
}