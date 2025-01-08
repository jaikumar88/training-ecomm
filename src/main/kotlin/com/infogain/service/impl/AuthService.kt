package com.infogain.service.impl

import com.infogain.dto.User
import com.infogain.repository.UserRepository
import org.jetbrains.exposed.sql.transactions.transaction

class AuthService(private val userRepository: UserRepository) {
    fun authenticate(email: String, password: String): User? {
        return transaction {
            // Fetch user by email
            val user = userRepository.findByEmail(email)
            user?.roles?.toList()
            println("User found: $user")

            // Log roles to confirm they are fetched
            if (user != null) {
                println("User roles: ${user.roles.map { it.name }}")
            }

            // Validate password (consider hashing in a real application)
            if (user != null && user.password == password) {
                println("Authentication successful for user: ${user.email}")
                return@transaction user
            } else {
                println("Invalid credentials for email: $email")
                null
            }
        }
    }

    private fun hashPassword(password: String): String {
        // Add password hashing logic here (e.g., BCrypt)
        return password // Placeholder
    }
}
