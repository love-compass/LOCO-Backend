package com.loco.exception

import lombok.Getter
import org.springframework.http.HttpStatus

@Getter
class LocoException(var httpStatus: HttpStatus, var body: ExceptionResponse): RuntimeException(){

}