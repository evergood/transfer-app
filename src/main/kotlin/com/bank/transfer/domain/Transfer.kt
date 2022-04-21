package com.bank.transfer.domain

import java.time.LocalDateTime
import java.util.*

data class Transfer(
    val id: UUID = UUID.randomUUID(),
    val amount: Long,
    val from: Long,
    val to: Long,
    val date: LocalDateTime = LocalDateTime.now()
)