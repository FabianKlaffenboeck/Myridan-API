package com.dynaHowl.myridan.model

import kotlinx.serialization.Serializable
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.javatime.datetime


object Parts : IntIdTable("Parts") {

    val name = varchar("name", 100)
    val quantity = integer("quantity")
    val value = double("value").nullable()

    val electricalUnit_id = enumeration<ElectricalUnit>("electricalUnit_id").nullable()
    val footprint_id = reference("footprint_id", Footprints).nullable()
    val partType_id = reference("partType_id", PartTypes)
    val manufacturer_id = reference("manufacturer_id", Manufacturers)
    val tray_id = reference("tray_id", Trays)

    val updatedAt = datetime("updatedAt").nullable()
    val deletedAt = datetime("deletedAt").nullable()
}


class PartEntity(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<PartEntity>(Parts)

    var name by Parts.name
    var quantity by Parts.quantity
    var value by Parts.value
    var electricalUnit by Parts.electricalUnit_id
    var footprint by FootprintEntity optionalReferencedOn Parts.footprint_id
    var partType by PartTypeEntity referencedOn Parts.partType_id
    var manufacturer by ManufacturerEntity referencedOn Parts.manufacturer_id
    var tray by TrayEntity referencedOn Parts.tray_id

    var updatedAt by Parts.updatedAt
    var deletedAt by Parts.deletedAt

    fun toPart() = Part(
        id.value,
        name,
        quantity,
        value,
        electricalUnit,
        footprint?.toFootprint(),
        partType.toPartType(),
        manufacturer.toManufacturer(),
        tray.toTray(),
    )
}

@Serializable
data class Part(
    var id: Int?,
    var name: String,
    var quantity: Int,
    var value: Double?,
    var electricalUnit: ElectricalUnit?,
    var footprint: Footprint?,
    var partType: PartType,
    var manufacturer: Manufacturer,
    var tray: Tray,
)