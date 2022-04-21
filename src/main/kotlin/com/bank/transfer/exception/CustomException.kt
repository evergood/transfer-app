package com.bank.transfer.exception

data class CustomException(val toAccount: Long, val description: String) :
    RuntimeException("Transaction to account #$toAccount failed: $description")