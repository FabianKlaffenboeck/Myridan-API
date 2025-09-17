package com.dynaHowl.myridan.services

import com.dynaHowl.myridan.model.User
import com.dynaHowl.myridan.model.UserEntity
import com.dynaHowl.myridan.model.Users
import org.jetbrains.exposed.sql.Op
import org.jetbrains.exposed.sql.transactions.transaction
import java.time.LocalDateTime

class UserService {

    fun getAll(): List<User> = transaction {
        val query = Op.Companion.build { Users.deletedAt.isNull() }
        UserEntity.Companion.find(query).map(UserEntity::toUser)
    }

    fun getById(id: Int): User? = transaction {
        UserEntity.Companion.find {
            Users.id eq id
        }.firstOrNull()?.toUser()
    }

    fun getByUsername(username: String): User? = transaction {
        UserEntity.Companion.find {
            Users.username eq username
        }.firstOrNull()?.toUser()
    }

    fun add(user: User): User = transaction {
        UserEntity.Companion.new {
            username = user.username
            passwordHash = user.passwordHash

            updatedAt = LocalDateTime.now()
        }.toUser()
    }

    fun update(user: User): User = transaction {
        val notNullId = user.id ?: -1

        UserEntity.Companion[notNullId].username = user.username
        UserEntity.Companion[notNullId].passwordHash = user.passwordHash

        UserEntity.Companion[notNullId].updatedAt = LocalDateTime.now()
        UserEntity.Companion[notNullId].toUser()
    }

    fun delete(id: Int) = transaction {
        UserEntity.Companion[id].deletedAt = LocalDateTime.now()
    }
}