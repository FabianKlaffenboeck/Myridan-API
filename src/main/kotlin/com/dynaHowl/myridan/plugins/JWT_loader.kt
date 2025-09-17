package com.dynaHowl.myridan.plugins

import io.ktor.server.application.Application

data class JwtConfig(
    val domain: String,
    val audience: String,
    val realm: String,
    val secret: String
)

fun Application.loadJwtConfig(): JwtConfig {
    val config = environment.config.config("jwt")
    return JwtConfig(
        domain = config.property("domain").getString(),
        audience = config.property("audience").getString(),
        realm = config.property("realm").getString(),
        secret = config.property("secret").getString()
    )
}