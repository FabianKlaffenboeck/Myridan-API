package com.dynaHowl.myridan.services

import com.dynaHowl.myridan.model.Shelf
import com.dynaHowl.myridan.model.ShelfEntity
import com.dynaHowl.myridan.model.Shelfs
import com.dynaHowl.myridan.model.TrayEntity
import org.jetbrains.exposed.sql.Op
import org.jetbrains.exposed.sql.SizedCollection
import org.jetbrains.exposed.sql.transactions.transaction
import java.time.LocalDateTime

class ShelfService {

    fun getAll(): List<Shelf> = transaction {
        val query = Op.build { Shelfs.deletedAt.isNull() }
        ShelfEntity.Companion.find(query).map(ShelfEntity::toShelf)
    }

    fun getById(id: Int): Shelf? = transaction {
        ShelfEntity.Companion.find {
            Shelfs.id eq id
        }.firstOrNull()?.toShelf()
    }

    fun add(shelf: Shelf): Shelf = transaction {

        for (i in 0..shelf.trays.size - 1) {
            if (TrayEntity.Companion.findById(shelf.trays[i].id ?: 0) == null) {
                shelf.trays[i].id = TrayService().add(shelf.trays[i]).id
            }
        }

        ShelfEntity.Companion.new {
            name = shelf.name
            trays = SizedCollection(shelf.trays.map {
                TrayEntity.Companion.findById(it.id ?: 0)!!
            })

            updatedAt = LocalDateTime.now()
        }.toShelf()
    }

    fun update(shelf: Shelf): Shelf = transaction {
        val notNullId = shelf.id ?: -1

        ShelfEntity.Companion[notNullId].name = shelf.name
        ShelfEntity.Companion[notNullId].trays = SizedCollection(shelf.trays.map {
            TrayEntity.Companion.findById(it.id ?: 0)!!
        })

        ShelfEntity.Companion[notNullId].updatedAt = LocalDateTime.now()
        ShelfEntity.Companion[notNullId].toShelf()
    }

    fun delete(id: Int) = transaction {
        ShelfEntity.Companion[id].deletedAt = LocalDateTime.now()
    }
}