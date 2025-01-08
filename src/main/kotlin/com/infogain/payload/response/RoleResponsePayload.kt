package com.infogain.payload.response



import kotlinx.serialization.Serializable
import java.util.UUID

@Serializable
data class RoleResponsePayload(
    val id: String,
    val name: String,
    val description: String?,
    val createdAt: String,
    val updatedAt: String
)
