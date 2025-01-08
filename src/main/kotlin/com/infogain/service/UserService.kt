package com.infogain.service

import com.infogain.payload.request.UserRequestPayload
import com.infogain.payload.response.UserResponsePayload

interface UserService {
    fun create(userPayload: UserRequestPayload): UserResponsePayload
    fun findAll(): List<UserResponsePayload>
    fun findById(id: String): UserResponsePayload
    fun update(id: String, userPayload: UserRequestPayload): UserResponsePayload
    fun delete(id: String): Boolean
}
