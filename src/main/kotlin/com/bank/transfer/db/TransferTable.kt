package com.bank.transfer.db

import org.jetbrains.exposed.dao.id.UUIDTable
import org.jetbrains.exposed.sql.javatime.datetime

object TransferTable : UUIDTable("transfer") {
    val amount = long("amount")
    val from = long("account_from")
    val to = long("account_to")
    val date = datetime("date")
}