package com.bank.transfer.controller

import com.bank.transfer.controller.dto.TransferRequest
import com.bank.transfer.db.AccountRepository
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@Tag(name = "Transfer", description = "Transfer API")
@RestController
@RequestMapping("/transfer")
class TransferController(
    private val accountRepository: AccountRepository
) {
    @Operation(summary = "Execute transfer")
    @ApiResponses(
        value = [
            ApiResponse(
                responseCode = "200",
                description = "Transfer succeeded",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(implementation = String::class)
                    )
                ]
            ),
            ApiResponse(
                responseCode = "400",
                description = "Transfer failed",
                content = [
                    Content(
                        mediaType = "application/json",
                        schema = Schema(implementation = String::class)
                    )
                ]
            )
        ]
    )
    @PostMapping
    fun transfer(@RequestBody @Valid request: TransferRequest): ResponseEntity<Any> {
        accountRepository.executeTransfer(request)
        return ResponseEntity.ok("Transfer succeeded")
    }

}