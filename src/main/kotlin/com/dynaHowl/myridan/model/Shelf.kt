package com.dynaHowl.myridan.model

import kotlinx.serialization.Serializable
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.datetime


object Shelfs : IntIdTable("Shelfs") {
    val name = varchar("name", 100)

    val updatedAt = datetime("updatedAt").nullable()
    val deletedAt = datetime("deletedAt").nullable()
}

object ShelfTrays : Table("shelf_trays") {
    val shelf_id = reference("shelf_id", Shelfs)
    val tray_id = reference("tray_id", Trays)
    override val primaryKey = PrimaryKey(
        shelf_id, tray_id, name = "PK_Shelf_tray_trayId"
    )
}

class ShelfEntity(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<ShelfEntity>(Shelfs)

    var name by Shelfs.name

    var trays by TrayEntity via ShelfTrays

    var updatedAt by Shelfs.updatedAt
    var deletedAt by Shelfs.deletedAt

    fun toShelf() = Shelf(
        id.value,
        name,
        trays.map { it.toTray() }
    )
}

@Serializable
data class Shelf(
    var id: Int?,
    var name: String,
    var trays: List<Tray>
)