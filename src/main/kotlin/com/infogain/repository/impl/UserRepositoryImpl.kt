package com.infogain.repository.impl

import com.infogain.dto.Role
import com.infogain.dto.User
import com.infogain.dto.UsersTable
import com.infogain.exceptions.NotFoundException
import com.infogain.payload.request.UserRequestPayload
import com.infogain.repository.UserRepository
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.toKotlinLocalDateTime
import org.jetbrains.exposed.dao.load
import org.jetbrains.exposed.sql.SizedCollection
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.UUID

class UserRepositoryImpl : UserRepository {
    override fun create(userPayload: UserRequestPayload): User = transaction {
        val user = User.new {
            firstName = userPayload.firstName
            lastName = userPayload.lastName
            email = userPayload.email
            password = userPayload.password
        }

        // Assign roles to the user
        val roles = userPayload.roleIds.map { roleId ->
            Role.findById(UUID.fromString(roleId)) ?: throw NotFoundException("Role not found with ID $roleId")
        }
        user.roles = SizedCollection(roles)

        user
    }

    override fun findAll(): List<User> = transaction { User.all().toList() }

    override fun findById(id: String): User? = transaction { User.findById(UUID.fromString(id)) }

    override fun update(id: String, userPayload: UserRequestPayload): User? = transaction {
        val user = User.findById(UUID.fromString(id)) ?: return@transaction null

        val roles = userPayload.roleIds.map { roleId ->
            Role.findById(UUID.fromString(roleId)) ?: throw NotFoundException("Role not found with ID $roleId")
        }

        println("roles details ${roles.toList()}")
        user.apply {
            firstName = userPayload.firstName
            lastName = userPayload.lastName
            email = userPayload.email
            password = userPayload.password
            this.roles = SizedCollection(roles)
        }
    }

    override fun delete(id: String): Boolean = transaction {
        val user = User.findById(UUID.fromString(id)) ?: return@transaction false
        user.delete()
        true
    }
    override fun findByEmail(email: String): User? {
        return transaction {
            User.find { UsersTable.email eq email }
                .singleOrNull()
                ?.also { user ->
                    // Force eager loading of roles
                    user.roles.toList()
                    println("Roles loaded: ${user.roles.map { it.name }}")
                }
        }
    }
}

