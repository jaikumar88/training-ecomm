package com.infogain.service.impl

import com.infogain.exceptions.NotFoundException
import com.infogain.extensions.toResponse
import com.infogain.payload.request.RoleRequestPayload
import com.infogain.payload.response.RoleResponsePayload
import com.infogain.repository.RoleRepository
import com.infogain.service.RoleService

import java.time.format.DateTimeFormatter

class RoleServiceImpl(private val roleRepository: RoleRepository) : RoleService {
    override fun create(rolePayload: RoleRequestPayload): RoleResponsePayload {
        val role = roleRepository.create(rolePayload)
        return RoleResponsePayload(
            id = role.id.value.toString(),
            name = role.name,
            description = role.description,
            createdAt = role.createdAt.toString(),
            updatedAt = role.updatedAt.toString()
        )
    }

    override fun findAll(): List<RoleResponsePayload> {
        return roleRepository.findAll().map {
            RoleResponsePayload(
                id = it.id.value.toString(),
                name = it.name,
                description = it.description,
                createdAt = it.createdAt.toString(),
                updatedAt = it.updatedAt.toString()
            )
        }
    }

    override fun findById(id: String): RoleResponsePayload {
        val role = roleRepository.findById(id) ?: throw NotFoundException("Role not found with ID $id")
        return RoleResponsePayload(
            id = role.id.value.toString(),
            name = role.name,
            description = role.description,
            createdAt = role.createdAt.toString(),
            updatedAt = role.updatedAt.toString()
        )
    }

    override fun delete(id: String): Boolean {
        if (!roleRepository.delete(id)) throw NotFoundException("Role not found with ID $id")
        return true
    }
}


