package com.infogain.dto



import org.jetbrains.exposed.dao.UUIDEntity
import org.jetbrains.exposed.dao.UUIDEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.kotlin.datetime.CurrentDateTime

import org.jetbrains.exposed.sql.kotlin.datetime.datetime
import java.util.UUID

object UsersTable : UUIDTable("users") {
    // UUID --> are the PK
    // no need to declare the PK
    val firstName = varchar("first_name", 50)
    val lastName = varchar("last_name", 50)
    val email = varchar("email", 100).uniqueIndex()
    val password = varchar("password", 255)
    //val roles = reference("role_id", RolesTable)
    val createdAt = datetime("created_at").defaultExpression(CurrentDateTime) // Proper initialization
    val updatedAt = datetime("updated_at").defaultExpression(CurrentDateTime)
}

class User(id: EntityID<UUID>) : UUIDEntity(id) {
    companion object : UUIDEntityClass<User>(UsersTable)

    var firstName by UsersTable.firstName
    var lastName by UsersTable.lastName
    var email by UsersTable.email
    var password by UsersTable.password
   var roles by Role via UserRolesTable
    var createdAt by UsersTable.createdAt
    var updatedAt by UsersTable.updatedAt
}

// earlier code : entity class ===> exposed (we were writing the complete statements
// now : entity class ==> exposed==> exposed-dao ==> exposed dao is consumed(find / update)
// composite demo