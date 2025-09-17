package com.dynaHowl.myridan.model

import kotlinx.serialization.Serializable
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.javatime.datetime


object Footprints : IntIdTable("Footprints") {
    val metric = varchar("metric", 100)
    val imperial = varchar("imperial", 100)

    val updatedAt = datetime("updatedAt").nullable()
    val deletedAt = datetime("deletedAt").nullable()
}

class FootprintEntity(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<FootprintEntity>(Footprints)

    var metric by Footprints.metric
    var imperial by Footprints.imperial

    var updatedAt by Footprints.updatedAt
    var deletedAt by Footprints.deletedAt

    fun toFootprint() = Footprint(
        id.value, metric, imperial
    )
}

@Serializable
data class Footprint(
    var id: Int?, var metric: String, var imperial: String
)