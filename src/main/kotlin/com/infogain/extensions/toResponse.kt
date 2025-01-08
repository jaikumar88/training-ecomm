package com.infogain.extensions

import com.infogain.dto.Role
import com.infogain.dto.User
import com.infogain.payload.response.RoleResponsePayload
import com.infogain.payload.response.UserResponsePayload
import kotlinx.datetime.format
import kotlinx.datetime.toJavaLocalDateTime // Import to convert kotlinx.datetime to java.time
import org.jetbrains.exposed.sql.transactions.transaction
import java.time.format.DateTimeFormatter

fun User.toResponse(): UserResponsePayload {
    // Java's DateTimeFormatter for formatting date-time
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")

    return   UserResponsePayload(
        id = id.toString(),
        firstName = firstName,
        lastName = lastName,
        email = email,
        token = null,
       roles = roles.map {  it.toResponse()},
        // Convert kotlinx.datetime.LocalDateTime to java.time.LocalDateTime before formatting
        createdAt = createdAt.toJavaLocalDateTime().format(formatter),
        updatedAt = updatedAt.toJavaLocalDateTime().format(formatter)
    )
}


fun Role.toResponse(): RoleResponsePayload {
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
    return RoleResponsePayload(
        id = id.toString(),
        name = name,
        description = description,
        createdAt = createdAt.toJavaLocalDateTime().format(formatter),
        updatedAt = updatedAt.toJavaLocalDateTime().format(formatter)
    )
}

