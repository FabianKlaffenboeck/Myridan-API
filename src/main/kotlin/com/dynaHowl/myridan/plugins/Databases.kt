package com.dynaHowl.myridan.plugins

import com.dynaHowl.myridan.development.*
import com.dynaHowl.myridan.model.*
import com.dynaHowl.myridan.services.*
import io.ktor.server.application.*
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

val tables = listOf(Footprints, Trays, Shelfs, ShelfTrays, PartTypes, Manufacturers, Parts, Users)

fun Application.configureDatabases(
    dbUrl: String, dbUser: String, dbPW: String, updateSchema: Boolean, initDB: Boolean, populateDB: Boolean
) {

    Database.connect(url = dbUrl, user = dbUser, password = dbPW)

    if (initDB) {
        initDB()
    }

    if (updateSchema) {
        updateTables()
    }

    if (populateDB) {
        populateDB()
    }
}

fun initDB() {
    transaction {
        tables.forEach { table ->
            SchemaUtils.drop(table)
        }
    }
    transaction {
        tables.forEach { table ->
            SchemaUtils.create(table)
        }
    }
}

fun updateTables() {
    transaction {
        tables.forEach { table ->
            SchemaUtils.createMissingTablesAndColumns(table)
        }
    }
}

fun populateDB() {
    transaction {
        for (footprint in sampleFootprints) {
            FootprintService().add(footprint)
        }
    }
    transaction {
        for (tray in sampleTrays) {
            TrayService().add(tray)
        }
    }
    transaction {
        for (shelf in sampleShelfs) {
            ShelfService().add(shelf)
        }
    }
    transaction {
        for (manufacturer in sampleManufacturers) {
            ManufacturerService().add(manufacturer)
        }
    }
    transaction {
        for (partType in samplePartTypes) {
            PartTypeService().add(partType)
        }
    }
    transaction {
        for (part in sampleParts) {
            PartService().add(part)
        }
    }
    transaction {
        for (user in sampleUsers) {
            UserService().add(user)
        }
    }
}
