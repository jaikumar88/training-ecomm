package com.infogain.payload.request

import kotlinx.serialization.Serializable

import jakarta.validation.constraints.*
@Serializable

data class UserRequestPayload(

    @field:NotBlank(message = "First name must not be blank")
    @field:Size(max = 50, message = "First name must not exceed 50 characters")
    val firstName: String,

    @field:NotBlank(message = "Last name must not be blank")
    @field:Size(max = 50, message = "Last name must not exceed 50 characters")
    val lastName: String,

    @field:NotBlank(message = "Email must not be blank")
    @field:Email(message = "Invalid email format")
    val email: String,

    @field:NotBlank(message = "Password must not be blank")
    @field:Size(min = 8, message = "Password must be at least 8 characters long")
    val password: String,

    @field:NotEmpty(message = "At least one role ID must be provided")
    val roleIds: List<String>
)
