package com.akvelon.orderservice

import com.akvelon.orderservice.dal.controller.OrderCakeController
import com.akvelon.orderservice.dal.dao.OrderCakeRepository
import com.akvelon.orderservice.grpc.AssortmentServiceClient
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.netflix.eureka.EnableEurekaClient
import org.springframework.context.annotation.Bean

@SpringBootApplication
@EnableEurekaClient
open class OrderService {

    @Bean
    open fun orderCakeController(orderCakeRepository: OrderCakeRepository, assortmentServiceClient: AssortmentServiceClient): OrderCakeController =
            OrderCakeController(orderCakeRepository, assortmentServiceClient)
}

fun main(args: Array<String>) {
    runApplication<OrderService>(*args)
}