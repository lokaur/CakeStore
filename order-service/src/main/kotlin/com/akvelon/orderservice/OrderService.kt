package com.akvelon.orderservice

import com.akvelon.orderservice.dal.controller.OrderCakeController
import com.akvelon.orderservice.dal.dao.OrderCakeRepository
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.netflix.eureka.EnableEurekaClient
import org.springframework.context.annotation.Bean

@SpringBootApplication
@EnableEurekaClient
open class OrderService {

    @Bean
    open fun orderCakeController(orderCakeRepository: OrderCakeRepository): OrderCakeController =
            OrderCakeController(orderCakeRepository)
}

fun main(args: Array<String>) {
    runApplication<OrderService>(*args)
}