package com.akvelon.cakestore.assortmentservice

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.netflix.eureka.EnableEurekaClient

@SpringBootApplication
@EnableEurekaClient
class AssortmentService
fun main(args: Array<String>) {
    runApplication<AssortmentService>(*args)
}