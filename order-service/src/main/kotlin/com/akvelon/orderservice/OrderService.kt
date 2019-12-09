package com.akvelon.orderservice

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.netflix.eureka.EnableEurekaClient

@SpringBootApplication
@EnableEurekaClient
open class OrderService

fun main(args: Array<String>) {
    runApplication<OrderService>(*args)
}