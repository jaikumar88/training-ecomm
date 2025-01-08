package com.infogain

import com.infogain.config.appModule
import com.infogain.config.initPostgresDatabase
import com.infogain.exceptions.BadRequestException
import com.infogain.exceptions.NotFoundException
import com.infogain.exceptions.handlers.configureExceptionHandling
import com.infogain.routes.rootRoutes
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.routing.*

import io.ktor.server.application.Application
import io.ktor.server.plugins.contentnegotiation.ContentNegotiation

import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.plugins.statuspages.*
import org.kodein.di.DI


fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {


    initPostgresDatabase()

    val diContainer = DI {
        import(appModule)
    }
    install(ContentNegotiation) {

      json() // @RequestBody
    }

   configureExceptionHandling()

    routing {
        rootRoutes(diContainer)
    }


}
