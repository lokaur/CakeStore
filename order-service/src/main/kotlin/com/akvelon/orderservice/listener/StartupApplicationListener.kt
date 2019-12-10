package com.akvelon.orderservice.listener

import com.akvelon.orderservice.dal.controller.BakingCakeController
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.context.ApplicationListener
import org.springframework.context.event.ContextRefreshedEvent
import org.springframework.stereotype.Component

@Component
class StartupApplicationListener(val bakingCakeController: BakingCakeController) : ApplicationListener<ContextRefreshedEvent> {

    private val logger: Logger = LoggerFactory.getLogger(StartupApplicationListener::class.toString())

    override fun onApplicationEvent(p0: ContextRefreshedEvent) {
        logger.info("Application started. Starting baking process..")
        bakingCakeController.startAll()
    }
}