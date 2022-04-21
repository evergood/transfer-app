package com.bank.transfer.db

import org.jetbrains.exposed.dao.id.LongIdTable

object AccountTable : LongIdTable("account") {
    val name = varchar("name", 100)
    val email = varchar("email", 100)
    val amount = long("amount")
}