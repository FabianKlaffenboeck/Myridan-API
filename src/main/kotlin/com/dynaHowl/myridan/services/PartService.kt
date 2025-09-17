package com.dynaHowl.myridan.services

import com.dynaHowl.myridan.model.*
import org.jetbrains.exposed.sql.Op
import org.jetbrains.exposed.sql.transactions.transaction
import java.time.LocalDateTime

class PartService {

    fun getAll(): List<Part> = transaction {
        val query = Op.build { Parts.deletedAt.isNull() }
        PartEntity.find(query).map(PartEntity::toPart)
    }

    fun getById(id: Int): Part? = transaction {
        PartEntity.find {
            Parts.id eq id
        }.firstOrNull()?.toPart()
    }

    fun add(part: Part): Part = transaction {
        PartEntity.new {
            name = part.name
            quantity = part.quantity
            value = part.value
            electricalUnit = part.electricalUnit
            footprint = part.footprint?.id?.let { FootprintEntity.findById(it) }
            partType = part.partType.id?.let { PartTypeEntity.findById(it) }!!
            manufacturer = part.manufacturer.id?.let { ManufacturerEntity.findById(it) }!!
            tray = part.tray.id?.let { TrayEntity.findById(it) }!!

            updatedAt = LocalDateTime.now()
        }.toPart()
    }


    fun update(part: Part): Part = transaction {
        val notNullId = part.id ?: -1

        PartEntity[notNullId].name = part.name
        PartEntity[notNullId].quantity = part.quantity
        PartEntity[notNullId].value = part.value
        PartEntity[notNullId].electricalUnit = part.electricalUnit
        PartEntity[notNullId].footprint = part.footprint?.id?.let { FootprintEntity.findById(it) }
        PartEntity[notNullId].partType = part.partType.id?.let { PartTypeEntity.findById(it) }!!
        PartEntity[notNullId].manufacturer =
            part.manufacturer.id?.let { ManufacturerEntity.findById(it) }!!
        PartEntity[notNullId].tray = part.tray.id?.let { TrayEntity.findById(it) }!!

        PartEntity[notNullId].updatedAt = LocalDateTime.now()
        PartEntity[notNullId].toPart()
    }

    fun delete(id: Int) = transaction {
        PartEntity[id].deletedAt = LocalDateTime.now()
    }
}