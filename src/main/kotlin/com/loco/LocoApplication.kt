package com.loco

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class LocoApplication

fun main(args: Array<String>) {
	runApplication<LocoApplication>(*args)
}
