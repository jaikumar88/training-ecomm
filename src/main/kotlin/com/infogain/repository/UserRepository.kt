package com.infogain.repository

import com.infogain.dto.User
import com.infogain.payload.request.UserRequestPayload

interface UserRepository {
    fun create(userPayload: UserRequestPayload): User
    fun findAll(): List<User>
    fun findById(id: String): User?
    fun update(id: String, userPayload: UserRequestPayload): User?
    fun delete(id: String): Boolean
    fun findByEmail(email: String): User?
}
