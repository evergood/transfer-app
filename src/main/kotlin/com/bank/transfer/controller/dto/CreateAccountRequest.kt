package com.bank.transfer.controller.dto

import javax.validation.constraints.Email


data class CreateAccountRequest(
    val name: String,
    @field:Email
    val email: String,
    val amount: Long
)