package com.dynaHowl.myridan.services

import com.dynaHowl.myridan.model.Manufacturer
import com.dynaHowl.myridan.model.ManufacturerEntity
import com.dynaHowl.myridan.model.Manufacturers
import org.jetbrains.exposed.sql.Op
import org.jetbrains.exposed.sql.transactions.transaction
import java.time.LocalDateTime

class ManufacturerService {

    fun getAll(): List<Manufacturer> = transaction {
        val query = Op.build { Manufacturers.deletedAt.isNull() }
        ManufacturerEntity.Companion.find(query).map(ManufacturerEntity::toManufacturer)
    }

    fun getById(id: Int): Manufacturer? = transaction {
        ManufacturerEntity.Companion.find {
            Manufacturers.id eq id
        }.firstOrNull()?.toManufacturer()
    }

    fun add(manufacturer: Manufacturer): Manufacturer = transaction {
        ManufacturerEntity.Companion.new {
            name = manufacturer.name

            updatedAt = LocalDateTime.now()
        }.toManufacturer()
    }

    fun update(manufacturer: Manufacturer): Manufacturer = transaction {
        val notNullId = manufacturer.id ?: -1

        ManufacturerEntity.Companion[notNullId].name = manufacturer.name

        ManufacturerEntity.Companion[notNullId].updatedAt = LocalDateTime.now()
        ManufacturerEntity.Companion[notNullId].toManufacturer()
    }

    fun delete(id: Int) = transaction {
        ManufacturerEntity.Companion[id].deletedAt = LocalDateTime.now()
    }
}