package com.akvelon.orderservice.dal.controller

import com.akvelon.orderservice.dal.dao.OrderCakeRepository
import com.akvelon.orderservice.dal.enum.EOrderStatus
import com.akvelon.orderservice.dal.model.Order
import com.akvelon.orderservice.grpc.AssortmentServiceClient
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import java.util.*
import kotlin.concurrent.schedule

@Component
class BakingCakeController(private val orderCakeRepository: OrderCakeRepository, private val assortmentServiceClient: AssortmentServiceClient) {

    private val logger: Logger = LoggerFactory.getLogger(BakingCakeController::class.toString())

    fun startAll() {
        orderCakeRepository.resetActiveOrders()
        val pendingOrders: Array<Int>? = orderCakeRepository.getAllPendingOrdersIds()

        if (pendingOrders == null || pendingOrders.isEmpty()) {
            logger.info("Pending orders not found")
            return
        }

        logger.info("Found ${pendingOrders.size} pending orders. Starting baking..")

        for (orderId: Int in pendingOrders) {
            startBaking(orderId)
        }
    }

    fun startBaking(orderId: Int) {
        val order: Order? = orderCakeRepository.getOrderById(orderId)

        if (order == null || order.status != EOrderStatus.NEW) {
            return
        }

        logger.info("Starting baking order ${order.id}")

        assortmentServiceClient.getCakeById(order.cakeId) {
            if (it != null) {
                setOrderStatus(order.id, EOrderStatus.IN_PROGRESS)
                Timer("Baking ${order.id}", false).schedule(it.cookingTime.toLong()) {
                    setOrderStatus(order.id, EOrderStatus.DONE)
                }
            }
        }
    }

    private fun setOrderStatus(orderId: Int, status: EOrderStatus) {
        logger.info("Changing order status: orderId: $orderId; status: $status")
        orderCakeRepository.setOrderStatus(orderId, status.statusId)
    }
}