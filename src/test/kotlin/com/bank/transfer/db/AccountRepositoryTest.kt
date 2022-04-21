package com.bank.transfer.db

import com.bank.transfer.controller.dto.CreateAccountRequest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class AccountRepositoryTest {
    @Autowired
    lateinit var accountRepository: AccountRepository

    @Test
    fun saveAccount() {
        val request = CreateAccountRequest("Jonn Smith", "jhon@gmail.com", 100)
        val id = accountRepository.create(request)
        val result = accountRepository.getByID(id)
        assertAll(
            { assertEquals(request.name, result?.name) },
            { assertEquals(request.email, result?.email) },
            { assertEquals(request.amount, result?.amount) }
        )
    }
}