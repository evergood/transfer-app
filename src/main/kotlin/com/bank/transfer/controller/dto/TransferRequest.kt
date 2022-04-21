package com.bank.transfer.controller.dto

import javax.validation.constraints.Positive

data class TransferRequest(
    @field:Positive
    val amount: Long,
    val from: Long,
    val to: Long
)