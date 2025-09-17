package com.dynaHowl.myridan.services

import com.dynaHowl.myridan.model.Footprint
import com.dynaHowl.myridan.model.FootprintEntity
import com.dynaHowl.myridan.model.Footprints
import org.jetbrains.exposed.sql.Op
import org.jetbrains.exposed.sql.transactions.transaction
import java.time.LocalDateTime

class FootprintService {

    fun getAll(): List<Footprint> = transaction {
        val query = Op.build { Footprints.deletedAt.isNull() }
        FootprintEntity.Companion.find(query).map(FootprintEntity::toFootprint)
    }

    fun getById(id: Int): Footprint? = transaction {
        FootprintEntity.Companion.find {
            Footprints.id eq id
        }.firstOrNull()?.toFootprint()
    }

    fun add(footprint: Footprint): Footprint = transaction {
        FootprintEntity.Companion.new {
            metric = footprint.metric
            imperial = footprint.imperial

            updatedAt = LocalDateTime.now()
        }.toFootprint()
    }

    fun update(footprint: Footprint): Footprint = transaction {
        val notNullId = footprint.id ?: -1

        FootprintEntity.Companion[notNullId].metric = footprint.metric
        FootprintEntity.Companion[notNullId].imperial = footprint.imperial

        FootprintEntity.Companion[notNullId].updatedAt = LocalDateTime.now()
        FootprintEntity.Companion[notNullId].toFootprint()
    }

    fun delete(id: Int) = transaction {
        FootprintEntity.Companion[id].deletedAt = LocalDateTime.now()
    }
}