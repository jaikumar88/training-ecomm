package com.infogain.utils



import com.infogain.dto.Role
import com.infogain.dto.RolesTable
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.UUID



object RoleResolver {
    fun resolveRoleNames(roleIds: List<String>): List<String> {
        return transaction {
            val uuids = roleIds.map { UUID.fromString(it) }
            Role.find { RolesTable.id inList uuids }.map { it.name }
        }
    }
}

