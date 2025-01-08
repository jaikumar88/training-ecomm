package com.infogain.routes

import com.infogain.repository.UserRepository
import com.infogain.repository.impl.RoleRepositoryImpl
import com.infogain.repository.impl.UserRepositoryImpl
import com.infogain.service.RoleService
import com.infogain.service.UserService
import com.infogain.service.impl.AuthService
import com.infogain.service.impl.RoleServiceImpl
import com.infogain.service.impl.UserServiceImpl
import io.ktor.server.routing.*
import org.kodein.di.DI
import org.kodein.di.direct
import org.kodein.di.instance

import org.kodein.di.ktor.closestDI

fun Route.rootRoutes(di : DI){
// Resolve services from Kodein-DI
    val userService: UserService = di.direct.instance()
    val roleService: RoleService = di.direct.instance()
    val authService: AuthService = di.direct.instance()


    userRoutes(userService)

    roleRoutes(roleService)
    authRoutes(authService)
}