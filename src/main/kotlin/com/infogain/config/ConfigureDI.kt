package com.infogain.config



import com.infogain.di.repositoryModule
import com.infogain.di.serviceModule
import org.kodein.di.DI

val appModule = DI.Module("AppModule") {
    import(repositoryModule) // Import repository bindings
    import(serviceModule)    // Import service bindings
}
