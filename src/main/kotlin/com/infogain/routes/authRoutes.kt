package com.infogain.routes

import com.infogain.payload.request.AuthRequestPayload
import com.infogain.payload.response.AuthResponsePayload
import com.infogain.security.JwtUtils
import com.infogain.service.impl.AuthService
import io.ktor.http.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.authRoutes(authService: AuthService) {
    route("/auth") {
//        post("/login") {
//            // Receive and deserialize the request payload
//            val payload = call.receive<AuthRequestPayload>()
//
//            println("Attempting login with email: ${payload.email}")
//
//            // Validate user credentials
//            val user = authService.authenticate(payload.email, payload.password)
//                ?: return@post call.respond(HttpStatusCode.Unauthorized, mapOf("error" to "Invalid credentials"))
//
//            // Log fetched roles for debugging
//            println("Fetched roles: ${user.roles.map { it.name }}")
//
//            // Generate JWT token
//            val token = JwtUtils.generateToken(user.email, user.roles.mapNotNull { it.id.value.toString() })
//
//            println("Generated token: $token")
//
//            // Respond with token and user details
//            call.respond(
//                AuthResponsePayload(
//                    id = user.id.value.toString(),
//                    firstName = user.firstName,
//                    lastName = user.lastName,
//                    email = user.email,
//                    roles = user.roles.map { it.name },
//                    token = token
//                )
//            )
//        }

        post("/login") {
            try {
                val payload = call.receive<AuthRequestPayload>()

                println("Attempting login with email: ${payload.email}")

                // Validate user credentials
                val user = authService.authenticate(payload.email, payload.password)
                    ?: return@post call.respond(HttpStatusCode.Unauthorized, mapOf("error" to "Invalid credentials"))

                println("User authenticated: ${user.email}")

                // Process roles and handle null cases explicitly
                val roles = user.roles?.mapNotNull { role ->
                    if (role.name == null) {
                        println("Warning: Role name is null for role ID: ${role.id}")
                        null // Skip null role names
                    } else {
                        println("Role processed: ${role.name}")
                        role.name
                    }
                } ?: emptyList() // Default to an empty list if roles is null

                println("Roles for user ${user.email}: $roles")

                // Generate JWT token
                val token = JwtUtils.generateToken(user.email, user.roles?.map { it.id.value.toString() } ?: emptyList())

                println("Generated token for user ${user.email}")

                // Respond with user details and token
                call.respond(
                    AuthResponsePayload(
                        id = user.id.value.toString(),
                        firstName = user.firstName,
                        lastName = user.lastName,
                        email = user.email,
                        roles = roles, // Finalized roles list
                        token = token
                    )
                )
            } catch (e: Exception) {
                println("Error during /auth/login: ${e.message}")
                e.printStackTrace()
                call.respond(HttpStatusCode.InternalServerError, mapOf("error" to "An unexpected error occurred"))
            }
        }


    }
}
