package com.loco.exception

import lombok.extern.slf4j.Slf4j
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestController

@RestController
@Slf4j
class GlobalExceptionHandler {
    @ExceptionHandler(LocoException::class)
    fun handleLocoException(locoException: LocoException): ResponseEntity<ExceptionResponse>{
        return ResponseEntity.status(locoException.httpStatus).body(locoException.body)
    }
}