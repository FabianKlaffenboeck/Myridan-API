package com.dynaHowl.myridan.model

import kotlinx.serialization.Serializable
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.javatime.datetime


object Trays : IntIdTable("Trays") {
    val name = varchar("name", 100)

    val updatedAt = datetime("updatedAt").nullable()
    val deletedAt = datetime("deletedAt").nullable()
}

class TrayEntity(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<TrayEntity>(Trays)

    var name by Trays.name

    var updatedAt by Trays.updatedAt
    var deletedAt by Trays.deletedAt

    fun toTray() = Tray(
        id.value, name
    )
}

@Serializable
data class Tray(
    var id: Int?,
    var name: String
)