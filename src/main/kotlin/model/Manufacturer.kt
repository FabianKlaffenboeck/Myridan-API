package at.eWolveLabs.model

import kotlinx.serialization.Serializable
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.javatime.datetime


object Manufacturers : IntIdTable("Manufacturers") {
    val name = varchar("name", 100)

    val updatedAt = datetime("updatedAt").nullable()
    val deletedAt = datetime("deletedAt").nullable()
}

class ManufacturerEntity(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<ManufacturerEntity>(Manufacturers)

    var name by Manufacturers.name

    var updatedAt by Manufacturers.updatedAt
    var deletedAt by Manufacturers.deletedAt

    fun toManufacturer() = Manufacturer(
        id.value, name
    )
}

@Serializable
data class Manufacturer(
    var id: Int?,
    var name: String
)