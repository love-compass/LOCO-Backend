package com.loco

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController


@RestController
class HomeController {
    @GetMapping("/")
    fun Hello(): String{
       return "This is Loco Start Page"
    }
}