package com.akvelon.cakesstore.cakesassormentservice

import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class TestController {

    @RequestMapping("/")
    fun home(): String = "Hello from Docker!"
}