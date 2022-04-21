package com.bank.transfer.domain

data class Account(
    val id: Long,
    val name: String,
    val email: String,
    val amount: Long
)