package com.akvelon.cakestore.assortmentclient

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.client.discovery.EnableDiscoveryClient
import org.springframework.cloud.netflix.eureka.EnableEurekaClient

@SpringBootApplication
@EnableEurekaClient
@EnableDiscoveryClient
open class AssortmentServiceClient

fun main(args: Array<String>) {
    runApplication<AssortmentServiceClient>(*args)
}