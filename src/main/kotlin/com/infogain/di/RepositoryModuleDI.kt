package com.infogain.di




import com.infogain.repository.RoleRepository
import com.infogain.repository.UserRepository
import com.infogain.repository.impl.RoleRepositoryImpl
import com.infogain.repository.impl.UserRepositoryImpl
import org.kodein.di.DI
import org.kodein.di.bind
import org.kodein.di.singleton

val repositoryModule = DI.Module("RepositoryModule") {
    bind<UserRepository>() with singleton { UserRepositoryImpl() }
    bind<RoleRepository>() with singleton { RoleRepositoryImpl() }
}

