package com.akvelon.cakestore.assortmentservice

import com.akvelon.cakestore.assortmentservice.dal.controller.CakeController
import com.akvelon.cakestore.assortmentservice.dal.dao.CakeRepository
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.netflix.eureka.EnableEurekaClient
import org.springframework.context.annotation.Bean

@SpringBootApplication
@EnableEurekaClient
open class AssortmentService {

    @Bean
    open fun cakeController(cakeRepository: CakeRepository): CakeController = CakeController(cakeRepository)
}

fun main(args: Array<String>) {
    runApplication<AssortmentService>(*args)
}