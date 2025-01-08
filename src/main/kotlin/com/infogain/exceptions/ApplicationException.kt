package com.infogain.exceptions

open class ApplicationException(message: String) : RuntimeException(message)
class NotFoundException(message: String) : ApplicationException(message)
class UserNotFoundException(message: String): Exception(message) {
    override fun toString(): String {
        return super.toString()
    }
}

class BadRequestException(message: String) : RuntimeException(message)
