package com.akvelon.orderservice.dal.controller

import com.akvelon.cakesassortmentproto.GetCakeResponse
import com.akvelon.orderservice.dal.dao.OrderCakeRepository
import com.akvelon.orderservice.dal.enum.EOrderStatus
import com.akvelon.orderservice.exception.EntityNotFoundException
import com.akvelon.orderservice.grpc.AssortmentServiceClient
import io.grpc.Context
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class OrderCakeController(private val orderCakeRepository: OrderCakeRepository, private val assortmentServiceClient: AssortmentServiceClient) {

    private val logger: Logger = LoggerFactory.getLogger(OrderCakeController::class.toString())

    private fun callWithContext(func: () -> Unit) {
        Context.current().fork().call(func)
    }

    fun createOrder(cakeName: String, callback: (orderId: Int, cookingTime: Int) -> Unit) {
        lookupCake(cakeName) {
            if (it == null) {
                throw EntityNotFoundException()
            }

            logger.info("Cake ${it.name} lookup success, creating order..")
            val orderId = orderCakeRepository.addOrder(it.id, EOrderStatus.NEW.statusId)
            callback(orderId, it.cookingTime)
        }
    }

    fun getOrderStatus(orderId: Int): EOrderStatus? =
            EOrderStatus.fromInt(orderCakeRepository.getOrderStatus(orderId) ?: throw EntityNotFoundException())

    private fun lookupCake(name: String, callback: (cake: GetCakeResponse?) -> Unit) {
        callWithContext {
            assortmentServiceClient.getCakeByName(name) {
                callback(it)
            }
        }
    }
}