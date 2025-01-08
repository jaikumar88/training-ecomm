package com.infogain.repository

import com.infogain.dto.Role
import com.infogain.payload.request.RoleRequestPayload


interface RoleRepository {
    fun create(rolePayload: RoleRequestPayload): Role
    fun findAll(): List<Role>
    fun findById(id: String): Role?
    fun delete(id: String): Boolean
}
