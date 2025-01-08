package com.infogain.middleware


import com.infogain.security.JwtUtils
import com.infogain.utils.RoleResolver


import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*

suspend fun ApplicationCall.requireAuthToken(): String? {
    val token = request.headers["X-Auth-Token"]
    if (token == null || !JwtUtils.validateToken(token)) {
        respond(HttpStatusCode.Unauthorized, mapOf("error" to "Invalid or missing token"))
        return null
    }
    return token
}

suspend fun ApplicationCall.requireRole(requiredRoles: List<String>, token: String): Boolean {
    val userRoleIds = JwtUtils.extractRolesFromToken(token)
    println("userRoleIds: $userRoleIds")
    val userRoles = RoleResolver.resolveRoleNames(userRoleIds)

    println("User Roles: $userRoles")
    println("Required Roles: $requiredRoles")

    if (requiredRoles.any { it in userRoles }) {
        return true
    }

    respond(HttpStatusCode.Forbidden, mapOf("error" to "Access denied"))
    return false
}

//suspend fun ApplicationCall.requireRole(requiredRoles: List<String>, token: String): Boolean {
//    val userRoles = JwtUtils.extractRolesFromToken(token)
//
//    println(userRoles)
//    println(requiredRoles)
//    // Check if at least one required role is present in the user's roles
//    if (requiredRoles.any { it in userRoles }) {
//        return true
//    }
//
//    respond(HttpStatusCode.Forbidden, mapOf("error" to "Access denied"))
//    return false
//}

