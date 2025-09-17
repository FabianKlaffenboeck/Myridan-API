package com.dynaHowl.myridan.plugins

import com.dynaHowl.myridan.routes.*
import com.dynaHowl.myridan.services.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.plugins.openapi.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.plugins.swagger.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting(jwtConfig: JwtConfig) {
    install(StatusPages) {
        exception<Throwable> { call, cause ->
            call.respondText(text = "500: $cause", status = HttpStatusCode.InternalServerError)
        }
    }
    val userService = UserService()
    val manufacturerService = ManufacturerService()
    val partService = PartService()
    val partTypeService = PartTypeService()
    val shelfService = ShelfService()
    val trayService = TrayService()
    val footprintService = FootprintService()


    routing {
        route("/api") {
            registerRoute(userService)
            loginRoute(jwtConfig, userService)

            authenticate("basicAuth", "auth-jwt") {
                openAPI(path = "openapi")
                swaggerUI(path = "swaggerui")

                manufacturerRoute(manufacturerService)
                partRoute(partService)
                partTypeRoute(partTypeService)
                shelfRoute(shelfService)
                trayRoute(trayService)
                footprintRoute(footprintService)
                electricalUnitRoute()
            }
        }
    }
}
