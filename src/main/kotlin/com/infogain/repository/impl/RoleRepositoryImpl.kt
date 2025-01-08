package com.infogain.repository.impl

import com.infogain.dto.Role
import com.infogain.dto.RolesTable
import com.infogain.exceptions.BadRequestException
import com.infogain.payload.request.RoleRequestPayload
import com.infogain.repository.RoleRepository
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq


import org.jetbrains.exposed.sql.transactions.transaction
import java.util.UUID

class RoleRepositoryImpl : RoleRepository {
    override fun create(rolePayload: RoleRequestPayload): Role = transaction {
        Role.new {
            name = rolePayload.name
            description = rolePayload.description
        }
    }

    override fun findAll(): List<Role> = transaction {
        Role.all().toList()
    }

    override fun findById(id: String): Role? = transaction {
        Role.findById(UUID.fromString(id))
    }

    override fun delete(id: String): Boolean = transaction {
        val role = Role.findById(UUID.fromString(id)) ?: return@transaction false
        role.delete()
        true
    }
}
