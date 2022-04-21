package com.bank.transfer.controller

import com.bank.transfer.controller.dto.CreateAccountRequest
import com.bank.transfer.db.AccountRepository
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.junit.jupiter.api.Test
import org.mockito.Mockito.`when`
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest(AccountController::class)
class AccountControllerTest {
    @Autowired
    lateinit var mvc: MockMvc

    @MockBean
    lateinit var accountRepository: AccountRepository

    @Test
    fun createAccount() {
        val request = CreateAccountRequest("Jonn Smith", "jhon@gmail.com", 100)
        `when`(accountRepository.create(request)).thenReturn(777)
        mvc.perform(
            MockMvcRequestBuilders.post("/card/limits")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(jacksonObjectMapper().writeValueAsString(request))
        ).andExpectAll(
            { status().isOk },
            { MockMvcResultMatchers.content().string("Account 777 created") }
        )
    }
}