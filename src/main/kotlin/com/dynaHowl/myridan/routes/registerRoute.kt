package com.dynaHowl.myridan.routes

import com.dynaHowl.myridan.model.User
import com.dynaHowl.myridan.services.PasswordHasher
import com.dynaHowl.myridan.services.UserService
import io.ktor.http.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

data class RegisterRequest(val username: String, val password: String)

fun Route.registerRoute(userService: UserService) {
    route("/register") {
        post {

            val req = call.receive<RegisterRequest>()
            val hashedPassword = PasswordHasher.hash(req.password)

            if (userService.getByUsername(req.username) != null) {
                return@post call.respond(HttpStatusCode.Conflict)
            }

            userService.add(User(null, req.username, hashedPassword))

            call.respond(HttpStatusCode.Created, "User registered")
        }
    }
}