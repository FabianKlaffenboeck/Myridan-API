package com.dynaHowl.myridan

import com.dynaHowl.myridan.plugins.configureDatabases
import com.dynaHowl.myridan.plugins.configureHTTP
import com.dynaHowl.myridan.plugins.configureRouting
import com.dynaHowl.myridan.plugins.configureSecurity
import com.dynaHowl.myridan.plugins.configureSerialization
import com.dynaHowl.myridan.plugins.loadJwtConfig
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*

fun main(args: Array<String>) {
    EngineMain.main(args)
}

fun Application.module() {

    val dbUrl: String = System.getenv("DBURL") ?: "jdbc:sqlite:myridan.sqlite"

    val dbUser: String = System.getenv("DBUSER") ?: "root"
    val dbPW: String = System.getenv("DBPW") ?: ""

    val initDB: Boolean = System.getenv("INITDB").toBoolean()
    val populateDB: Boolean = System.getenv("POPULATEDB").toBoolean()
    val updateSchema: Boolean = System.getenv("UPDATESCHEMA").toBoolean()

    val jwtConfig = loadJwtConfig()

    configureDatabases(dbUrl, dbUser, dbPW, updateSchema, initDB, populateDB)
    configureHTTP()
    configureSerialization()

    configureSecurity(jwtConfig)

    configureRouting(jwtConfig)
}
