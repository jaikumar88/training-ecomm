package com.infogain.payload.response

import kotlinx.serialization.Serializable

@Serializable
data class AuthResponsePayload(
    val id: String,
    val firstName: String,
    val lastName: String,
    val email: String,
    val roles: List<String>,
    val token: String
)
