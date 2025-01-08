package com.infogain.dto

import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.kotlin.datetime.CurrentDateTime
import org.jetbrains.exposed.sql.kotlin.datetime.datetime
import org.jetbrains.exposed.dao.id.IdTable
import org.jetbrains.exposed.sql.ReferenceOption // Optional, for configuring reference options
import org.jetbrains.exposed.sql.Table

object UserRolesTable : Table("user_roles") {
    val user = reference("user_id", UsersTable)
    val role = reference("role_id", RolesTable)
    override val primaryKey = PrimaryKey(user, role)
}

