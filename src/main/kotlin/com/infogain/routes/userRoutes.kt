package com.infogain.routes

import com.infogain.middleware.requireAuthToken
import com.infogain.middleware.requireRole
import com.infogain.payload.request.UserRequestPayload
import com.infogain.security.JwtUtils
import com.infogain.service.UserService
import com.infogain.utils.validate
import io.ktor.http.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.userRoutes(userService: UserService) {
    route("/users") {
        post {
            val payload = call.receive<UserRequestPayload>().apply { validate() }
            call.respond(HttpStatusCode.Created, userService.create(payload))
        }
        get {
            val token = call.requireAuthToken() ?: return@get
            if (!call.requireRole(listOf("Admin", "manager"), token)) return@get
            call.respond(userService.findAll())
        }

        get("/{id}") {
            val token = call.requireAuthToken() ?: return@get
            if (!call.requireRole(listOf("Admin", "manager"), token)) return@get

            val id = call.parameters["id"]!!
            call.respond(userService.findById(id))
        }

        put("/{id}") {
            val token = call.requireAuthToken() ?: return@put
            if (!call.requireRole(listOf("Admin"), token)) return@put

            val id = call.parameters["id"]!!
            val payload = call.receive<UserRequestPayload>()
            call.respond(userService.update(id, payload))
        }

        delete("/{id}") {
            val token = call.requireAuthToken() ?: return@delete
            if (!call.requireRole(listOf("Admin"), token)) return@delete

            val id = call.parameters["id"]!!
            userService.delete(id)
            call.respond(HttpStatusCode.NoContent)
        }
//        delete("/{id}") {
//            try {
//                // Retrieve and validate the token from the custom header
//                val token = call.request.headers["X-Auth-Token"]
//                    ?: throw IllegalArgumentException("Missing X-Auth-Token header")
//
//                if (!JwtUtils.validateToken(token)) {
//                    throw IllegalArgumentException("Invalid token")
//                }
//
//                // Extract the email from the token
//                val email = JwtUtils.extractEmailFromToken(token)
//                    ?: throw IllegalArgumentException("Invalid token payload")
//
//                // Get user ID from the path parameter
//                val id = call.parameters["id"] ?: throw IllegalArgumentException("Missing user ID")
//
//                // Fetch the user and verify authorization
//                val user = userService.findById(id)
//                if (user.email != email) {
//                    call.respond(HttpStatusCode.Forbidden, mapOf("error" to "You are not authorized to delete this user"))
//                    return@delete
//                }
//
//                // Perform the delete operation
//                userService.delete(id)
//                call.respond(HttpStatusCode.NoContent)
//            } catch (e: IllegalArgumentException) {
//                call.respond(HttpStatusCode.Unauthorized, mapOf("error" to e.message))
//            } catch (e: Exception) {
//                call.respond(HttpStatusCode.InternalServerError, mapOf("error" to "An unexpected error occurred"))
//            }
//        }

    }
}

