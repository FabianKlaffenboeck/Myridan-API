package com.dynaHowl.myridan.plugins

import com.dynaHowl.myridan.routes.electricalUnitRoute
import com.dynaHowl.myridan.routes.footprintRoute
import com.dynaHowl.myridan.routes.loginRoute
import com.dynaHowl.myridan.routes.manufacturerRoute
import com.dynaHowl.myridan.routes.partRoute
import com.dynaHowl.myridan.routes.partTypeRoute
import com.dynaHowl.myridan.routes.registerRoute
import com.dynaHowl.myridan.routes.shelfRoute
import com.dynaHowl.myridan.routes.trayRoute
import com.dynaHowl.myridan.services.FootprintService
import com.dynaHowl.myridan.services.ManufacturerService
import com.dynaHowl.myridan.services.PartService
import com.dynaHowl.myridan.services.PartTypeService
import com.dynaHowl.myridan.services.ShelfService
import com.dynaHowl.myridan.services.TrayService
import com.dynaHowl.myridan.services.UserService
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

    routing {
        route("/api") {
            registerRoute(userService)
            loginRoute(jwtConfig, userService)

            authenticate("basicAuth", "auth-jwt") {
                openAPI(path = "openapi")
                swaggerUI(path = "swaggerui")

                manufacturerRoute(ManufacturerService())
                partRoute(PartService())
                partTypeRoute(PartTypeService())
                shelfRoute(ShelfService())
                trayRoute(TrayService())
                footprintRoute(FootprintService())
                electricalUnitRoute()
            }
        }
    }
}
