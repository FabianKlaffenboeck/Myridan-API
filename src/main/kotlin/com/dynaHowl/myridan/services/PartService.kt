package com.dynaHowl.myridan.services

import com.dynaHowl.myridan.model.FootprintEntity
import com.dynaHowl.myridan.model.ManufacturerEntity
import com.dynaHowl.myridan.model.Part
import com.dynaHowl.myridan.model.PartEntity
import com.dynaHowl.myridan.model.PartTypeEntity
import com.dynaHowl.myridan.model.Parts
import com.dynaHowl.myridan.model.TrayEntity
import org.jetbrains.exposed.sql.Op
import org.jetbrains.exposed.sql.transactions.transaction
import java.time.LocalDateTime

class PartService {

    fun getAll(): List<Part> = transaction {
        val query = Op.build { Parts.deletedAt.isNull() }
        PartEntity.Companion.find(query).map(PartEntity::toPart)
    }

    fun getById(id: Int): Part? = transaction {
        PartEntity.Companion.find {
            Parts.id eq id
        }.firstOrNull()?.toPart()
    }

    fun add(part: Part): Part = transaction {
        PartEntity.Companion.new {
            name = part.name
            quantity = part.quantity
            value = part.value
            electricalUnit = part.electricalUnit
            footprint = part.footprint?.id?.let { FootprintEntity.Companion.findById(it) }
            partType = part.partType.id?.let { PartTypeEntity.findById(it) }!!
            manufacturer = part.manufacturer.id?.let { ManufacturerEntity.Companion.findById(it) }!!
            tray = part.tray.id?.let { TrayEntity.Companion.findById(it) }!!

            updatedAt = LocalDateTime.now()
        }.toPart()
    }


    fun update(part: Part): Part = transaction {
        val notNullId = part.id ?: -1

        PartEntity.Companion[notNullId].name = part.name
        PartEntity.Companion[notNullId].quantity = part.quantity
        PartEntity.Companion[notNullId].value = part.value
        PartEntity.Companion[notNullId].electricalUnit = part.electricalUnit
        PartEntity.Companion[notNullId].footprint = part.footprint?.id?.let { FootprintEntity.Companion.findById(it) }
        PartEntity.Companion[notNullId].partType = part.partType.id?.let { PartTypeEntity.findById(it) }!!
        PartEntity.Companion[notNullId].manufacturer = part.manufacturer.id?.let { ManufacturerEntity.Companion.findById(it) }!!
        PartEntity.Companion[notNullId].tray = part.tray.id?.let { TrayEntity.Companion.findById(it) }!!

        PartEntity.Companion[notNullId].updatedAt = LocalDateTime.now()
        PartEntity.Companion[notNullId].toPart()
    }

    fun delete(id: Int) = transaction {
        PartEntity.Companion[id].deletedAt = LocalDateTime.now()
    }
}