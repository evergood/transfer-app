package com.bank.transfer.exception

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus

@ControllerAdvice
class Handler {

    @ExceptionHandler(MethodArgumentNotValidException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun validHandler(x: MethodArgumentNotValidException): ResponseEntity<String> {
        return ResponseEntity.badRequest().body(
            "'${x.fieldError?.field}' has invalid value '${x.fieldError?.rejectedValue}' (${x.fieldError?.defaultMessage})"
        )
    }

    @ExceptionHandler(CustomException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun customHandler(x: CustomException): ResponseEntity<String> {
        return ResponseEntity.badRequest().body(x.message)
    }
}