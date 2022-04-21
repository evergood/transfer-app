package com.bank.transfer.controller

import com.bank.transfer.controller.dto.TransferRequest
import com.bank.transfer.db.AccountRepository
import com.bank.transfer.exception.CustomException
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.junit.jupiter.api.Test
import org.mockito.Mockito.`when`
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest
class TransferControllerTest {

    @Autowired
    lateinit var mvc: MockMvc

    @MockBean
    lateinit var accountRepository: AccountRepository

    @Test
    fun makeTransfer() {
        val request = TransferRequest(100, 10, 50)
        mvc.perform(
            MockMvcRequestBuilders.post("/transfer")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(jacksonObjectMapper().writeValueAsString(request))
        ).andExpectAll(
            { status().isOk },
            { content().string("Transfer succeeded") }
        )
    }

    @Test
    fun errorTransfer() {
        val request = TransferRequest(5000, 10, 50)
        `when`(accountRepository.executeTransfer(request)).thenThrow(
            CustomException(
                50,
                "Transaction to account #50 failed: Insufficient funds"
            )
        )
        mvc.perform(
            MockMvcRequestBuilders.post("/transfer")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(jacksonObjectMapper().writeValueAsString(request))
        ).andExpectAll(
            { status().isBadRequest },
            { content().string("Transaction to account #2 failed: Insufficient funds") }
        )
    }
}