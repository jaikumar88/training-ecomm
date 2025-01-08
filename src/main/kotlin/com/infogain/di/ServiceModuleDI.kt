package com.infogain.di

import com.infogain.repository.RoleRepository
import com.infogain.repository.UserRepository
import com.infogain.service.RoleService
import com.infogain.service.UserService
import com.infogain.service.impl.AuthService
import com.infogain.service.impl.RoleServiceImpl
import com.infogain.service.impl.UserServiceImpl
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.instance
import org.kodein.di.singleton

val serviceModule = DI.Module("ServiceModule") {
    bind<UserService>() with singleton { UserServiceImpl(instance<UserRepository>()) }
    bind<RoleService>() with singleton { RoleServiceImpl(instance<RoleRepository>()) }
    bind <AuthService>() with singleton { AuthService(instance<UserRepository>()) }
}
