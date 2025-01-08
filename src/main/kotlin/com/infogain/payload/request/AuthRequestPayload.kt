package com.infogain.payload.request

import kotlinx.serialization.Serializable

@Serializable
data class AuthRequestPayload(
    val email: String,
    val password: String
)
