package com.loco.exception

import lombok.Getter

@Getter
class ExceptionResponse (var errorCode: String, var message: String){
    fun of(errorType: ErrorType): ExceptionResponse {
        return ExceptionResponse(errorType.errorCode, errorType.message)
    }
}