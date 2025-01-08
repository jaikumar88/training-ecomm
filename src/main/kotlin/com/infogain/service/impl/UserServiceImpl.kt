package com.infogain.service.impl

import com.infogain.exceptions.NotFoundException
import com.infogain.exceptions.UserNotFoundException
import com.infogain.extensions.toResponse
import com.infogain.payload.request.UserRequestPayload
import com.infogain.payload.response.RoleResponsePayload
import com.infogain.payload.response.UserResponsePayload
import com.infogain.repository.UserRepository
import com.infogain.security.JwtUtils
import com.infogain.service.UserService
import org.jetbrains.exposed.sql.transactions.transaction


class UserServiceImpl(private val userRepository: UserRepository) : UserService {
    override fun create(userPayload: UserRequestPayload): UserResponsePayload {
        return transaction {
            val user = userRepository.create(userPayload)
            UserResponsePayload(
                id = user.id.value.toString(),
                firstName = user.firstName,
                lastName = user.lastName,
                email = user.email,
                createdAt = user.createdAt.toString(),
                updatedAt = user.updatedAt.toString(),

                roles = user.roles.map {
                    RoleResponsePayload(
                        id = it.id.value.toString(),
                        name = it.name,
                        description = it.description,
                        createdAt = it.createdAt.toString(),
                        updatedAt = it.updatedAt.toString()
                    )

                },
                token = JwtUtils.generateToken(user.email, userPayload.roleIds),
            )
        }
    }

    override fun findAll(): List<UserResponsePayload> {
        return transaction {
            userRepository.findAll().map {

                UserResponsePayload(
                    id = it.id.value.toString(),
                    firstName = it.firstName,
                    lastName = it.lastName,
                    email = it.email,
                    createdAt = it.createdAt.toString(),
                    updatedAt = it.updatedAt.toString(),

                    roles = it.roles.map { role ->
                        RoleResponsePayload(
                            id = role.id.value.toString(),
                            name = role.name,
                            description = role.description,
                            createdAt = role.createdAt.toString(),
                            updatedAt = role.updatedAt.toString()
                        )
                    }
                )
            }
        }
    }

    override fun findById(id: String): UserResponsePayload {
        val user = userRepository.findById(id) ?: throw NotFoundException("User not found with ID $id")
        return transaction {
            UserResponsePayload(
                id = user.id.value.toString(),
                firstName = user.firstName,
                lastName = user.lastName,
                email = user.email,
                createdAt = user.createdAt.toString(),
                updatedAt = user.updatedAt.toString(),

                roles = user.roles.map {
                    RoleResponsePayload(
                        id = it.id.value.toString(),
                        name = it.name,
                        description = it.description,
                        createdAt = it.createdAt.toString(),
                        updatedAt = it.updatedAt.toString()
                    )
                }
            )
        }
    }

    override fun update(id: String, userPayload: UserRequestPayload): UserResponsePayload {
        val user = userRepository.update(id, userPayload) ?: throw NotFoundException("User not found with ID $id")
        return transaction {
            UserResponsePayload(
                id = user.id.value.toString(),
                firstName = user.firstName,
                lastName = user.lastName,
                email = user.email,
                createdAt = user.createdAt.toString(),
                updatedAt = user.updatedAt.toString(),
                token = null,
                roles = user.roles.map {
                    RoleResponsePayload(
                        id = it.id.value.toString(),
                        name = it.name,
                        description = it.description,
                        createdAt = it.createdAt.toString(),
                        updatedAt = it.updatedAt.toString()
                    )
                }
            )
        }
    }

    override fun delete(id: String): Boolean {
        return transaction {
            if (!userRepository.delete(id)) throw NotFoundException("User not found with ID $id")
            true
        }

    }
}

