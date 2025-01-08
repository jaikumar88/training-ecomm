package com.infogain.service

import com.infogain.payload.request.RoleRequestPayload
import com.infogain.payload.response.RoleResponsePayload

interface RoleService {
    fun create(rolePayload: RoleRequestPayload): RoleResponsePayload
    fun findAll(): List<RoleResponsePayload>
    fun findById(id: String): RoleResponsePayload
    fun delete(id: String): Boolean
}
