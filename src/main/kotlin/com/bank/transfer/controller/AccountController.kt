package com.bank.transfer.controller

import com.bank.transfer.controller.dto.CreateAccountRequest
import com.bank.transfer.db.AccountRepository
import com.bank.transfer.domain.Account
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@Tag(name = "Accounts", description = "Accounts API")
@RestController
@RequestMapping("/account")
class AccountController(
    private val accountRepository: AccountRepository
) {
    @Operation(summary = "Create account")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "Account created",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(implementation = String::class)
                    )
                ]
            ),
            ApiResponse(
                responseCode = "400",
                description = "Violation of parameters, account not created",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(implementation = String::class)
                    )
                ]
            )
        ]
    )
    @PostMapping("/create")
    fun createAccount(@Valid @RequestBody account: CreateAccountRequest): ResponseEntity<Any> {
        val id = accountRepository.create(account)
        return ResponseEntity.ok("Account #$id created")
    }

    @Operation(summary = "Retrieve account")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "Account retreived",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(implementation = Account::class)
                    )
                ]
            ),
            ApiResponse(
                responseCode = "404",
                description = "Account doesn't exist",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(implementation = String::class)
                    )
                ]
            )
        ]
    )
    @GetMapping("/info/{id}")
    fun accountInfo(@PathVariable id: Long): ResponseEntity<Any> {
        val account = accountRepository.getByID(id)
        return if (account == null) {
            ResponseEntity.status(HttpStatus.NOT_FOUND).body("Account doesn't exist")
        } else ResponseEntity.ok(account)
    }
}