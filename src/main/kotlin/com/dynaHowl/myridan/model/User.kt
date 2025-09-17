package com.dynaHowl.myridan.model

import kotlinx.serialization.Serializable
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.javatime.datetime




object Users : IntIdTable("Users") {
    val username = varchar("username", 50).uniqueIndex()
    val passwordHash = varchar("password_hash", 64)

    val updatedAt = datetime("updatedAt").nullable()
    val deletedAt = datetime("deletedAt").nullable()
}

class UserEntity(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<UserEntity>(Users)

    var username by Users.username
    var passwordHash by Users.passwordHash

    var updatedAt by Users.updatedAt
    var deletedAt by Users.deletedAt

    fun toUser() = User(
        id.value, username, passwordHash
    )
}

@Serializable
data class User(
    var id: Int?,
    var username: String,
    var passwordHash: String
)