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
        TrayEntity.Companion.find(query).map(TrayEntity::toTray)
    }

    fun getById(id: Int): Tray? = transaction {
        TrayEntity.Companion.find {
            Trays.id eq id
        }.firstOrNull()?.toTray()
    }

    fun add(tray: Tray): Tray = transaction {
        TrayEntity.Companion.new {
            name = tray.name

            updatedAt = LocalDateTime.now()
        }.toTray()
    }

    fun update(tray: Tray): Tray = transaction {
        val notNullId = tray.id ?: -1

        TrayEntity.Companion[notNullId].name = tray.name

        TrayEntity.Companion[notNullId].updatedAt = LocalDateTime.now()
        TrayEntity.Companion[notNullId].toTray()
    }

    fun delete(id: Int) = transaction {
        TrayEntity.Companion[id].deletedAt = LocalDateTime.now()
    }
}