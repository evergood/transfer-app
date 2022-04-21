package com.bank.transfer.db

import com.bank.transfer.controller.dto.TransferRequest
import com.bank.transfer.exception.CustomException
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class TransferRepositoryTest {
    @Autowired
    lateinit var accountRepository: AccountRepository

    @Autowired
    lateinit var transferRepository: TransferRepository

    @Test
    fun proceedTransfer() {
        val request = TransferRequest(500, 1, 2)
        val transfer = accountRepository.executeTransfer(request)
        val account1 = accountRepository.getByID(1)
        val account2 = accountRepository.getByID(2)
        assertAll(
            { assertEquals(account1?.amount, 500) },
            { assertEquals(account2?.amount, 2500) }
        )
        val expected = transferRepository.getByUuid(transfer)
        assertAll(
            { assertEquals(expected?.amount, request.amount) },
            { assertEquals(expected?.from, request.from) },
            { assertEquals(expected?.to, request.to) }
        )
    }

    @Test
    fun failTransfer() {
        val request = TransferRequest(5000, 1, 2)
        val thrown = assertThrows<CustomException> { accountRepository.executeTransfer(request) }
        assertEquals(thrown.message, "Transaction to account #2 failed: Insufficient funds")
    }
}