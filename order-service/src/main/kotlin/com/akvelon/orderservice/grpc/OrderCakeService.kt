package com.akvelon.orderservice.grpc

import com.akvelon.ordercakeproto.*
import com.akvelon.orderservice.dal.controller.BakingCakeController
import com.akvelon.orderservice.dal.controller.OrderCakeController
import com.akvelon.orderservice.dal.enum.EOrderStatus
import com.akvelon.orderservice.exception.EntityNotFoundException
import io.grpc.stub.StreamObserver
import org.lognet.springboot.grpc.GRpcService
import org.slf4j.Logger
import org.slf4j.LoggerFactory

@GRpcService
class OrderCakeService(private val orderCakeController: OrderCakeController, private val bakingCakeController: BakingCakeController) : OrderCakeServiceGrpc.OrderCakeServiceImplBase() {

    private val logger: Logger = LoggerFactory.getLogger(OrderCakeService::class.toString())

    override fun orderCake(request: OrderCakeRequest?, responseObserver: StreamObserver<OrderCakeResponse>?) {
        try {
            logger.info("OrderCake: ${request?.cakeName}")

            if (request == null || request.cakeName.isEmpty()) {
                responseObserver?.onNext(makeOrderCakeErrorResponse(OrderCakeResponse.EnumOrderCakeStatus.INVALID_PARAMS))
            } else {
                orderCakeController.createOrder(request.cakeName) { id, cookingTime ->
                    bakingCakeController.startBaking(id)
                    responseObserver?.onNext(makeOrderCakeResponse(id, cookingTime))
                    responseObserver?.onCompleted()
                }
            }
        } catch (e: EntityNotFoundException) {
            logger.error("Cannot create an order: Cake ${request?.cakeName} does not exist")
            responseObserver?.onNext(makeOrderCakeErrorResponse(OrderCakeResponse.EnumOrderCakeStatus.CAKE_NOT_EXISTS))
            responseObserver?.onCompleted()
        } catch (e: Exception) {
            logger.error("Cannot create an order: ${e.message}")
            responseObserver?.onNext(makeOrderCakeErrorResponse(OrderCakeResponse.EnumOrderCakeStatus.UNKNOWN_ERROR))
            responseObserver?.onCompleted()
        }
    }

    private fun makeOrderCakeResponse(orderId: Int, cookingTime: Int): OrderCakeResponse =
            OrderCakeResponse
                    .newBuilder()
                    .setStatus(OrderCakeResponse.EnumOrderCakeStatus.OK)
                    .setOrderId(orderId)
                    .setCookingTime(cookingTime)
                    .build()

    private fun makeOrderCakeErrorResponse(status: OrderCakeResponse.EnumOrderCakeStatus): OrderCakeResponse =
            OrderCakeResponse
                    .newBuilder()
                    .setStatus(status)
                    .build()

    override fun checkOrderStatus(request: CheckOrderStatusRequest?, responseObserver: StreamObserver<CheckOrderStatusResponse>?) {
        try {
            logger.info("CheckOrder: ${request?.orderId}")
            if (request == null) {
                responseObserver?.onNext(makeCheckOrderStatusErrorResponse(CheckOrderStatusResponse.EnumCheckOrderStatus.INVALID_PARAMS))
            } else {
                val orderStatus: EOrderStatus? = orderCakeController.getOrderStatus(request.orderId)
                responseObserver?.onNext(makeCheckOrderStatusResponse(EOrderStatus.toEResponseOrderStatus(orderStatus!!)))
            }
        } catch (e: EntityNotFoundException) {
            responseObserver?.onNext(makeCheckOrderStatusErrorResponse(CheckOrderStatusResponse.EnumCheckOrderStatus.ORDER_NOT_FOUND))
        } catch (e: Exception) {
            responseObserver?.onNext(makeCheckOrderStatusErrorResponse(CheckOrderStatusResponse.EnumCheckOrderStatus.UNKNOWN_ERROR))
        } finally {
            responseObserver?.onCompleted()
        }
    }

    private fun makeCheckOrderStatusErrorResponse(status: CheckOrderStatusResponse.EnumCheckOrderStatus): CheckOrderStatusResponse =
            CheckOrderStatusResponse.newBuilder()
                    .setStatus(status)
                    .build()

    private fun makeCheckOrderStatusResponse(orderStatus: CheckOrderStatusResponse.EnumOrderStatus): CheckOrderStatusResponse =
            CheckOrderStatusResponse.newBuilder()
                    .setStatus(CheckOrderStatusResponse.EnumCheckOrderStatus.OK)
                    .setOrderStatus(orderStatus)
                    .build()
}