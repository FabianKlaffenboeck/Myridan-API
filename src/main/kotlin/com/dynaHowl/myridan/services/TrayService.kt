package com.dynaHowl.myridan.services

import com.dynaHowl.myridan.model.Tray
import com.dynaHowl.myridan.model.TrayEntity
import com.dynaHowl.myridan.model.Trays
import org.jetbrains.exposed.sql.Op
import org.jetbrains.exposed.sql.transactions.transaction
import java.time.LocalDateTime

class TrayService {

    fun getAll(): List<Tray> = transaction {
        val query = Op.build { Trays.deletedAt.isNull() }
        TrayEntity.find(query).map(TrayEntity::toTray)
    }

    fun getById(id: Int): Tray? = transaction {
        TrayEntity.find {
            Trays.id eq id
        }.firstOrNull()?.toTray()
    }

    fun add(tray: Tray): Tray = transaction {
        TrayEntity.new {
            name = tray.name

            updatedAt = LocalDateTime.now()
        }.toTray()
    }

    fun update(tray: Tray): Tray = transaction {
        val notNullId = tray.id ?: -1

        TrayEntity[notNullId].name = tray.name

        TrayEntity[notNullId].updatedAt = LocalDateTime.now()
        TrayEntity[notNullId].toTray()
    }

    fun delete(id: Int) = transaction {
        TrayEntity[id].deletedAt = LocalDateTime.now()
    }
}