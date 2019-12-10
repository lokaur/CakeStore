package com.akvelon.orderservice.grpc

import com.akvelon.ordercakeproto.OrderCakeRequest
import com.akvelon.ordercakeproto.OrderCakeResponse
import com.akvelon.ordercakeproto.OrderCakeServiceGrpc
import com.akvelon.orderservice.dal.controller.OrderCakeController
import com.akvelon.orderservice.exceptions.EntityNotFoundException
import io.grpc.Context
import io.grpc.stub.StreamObserver
import org.lognet.springboot.grpc.GRpcService
import org.slf4j.Logger
import org.slf4j.LoggerFactory

@GRpcService
class OrderCakeService(private val orderCakeController: OrderCakeController) : OrderCakeServiceGrpc.OrderCakeServiceImplBase() {

    private val logger: Logger = LoggerFactory.getLogger(OrderCakeService::class.toString())

    private fun callWithContext(func: () -> Unit) {
        Context.current().fork().call(func)
    }

    override fun orderCake(request: OrderCakeRequest?, responseObserver: StreamObserver<OrderCakeResponse>?) {
        try {
            logger.info("OrderCake: ${request?.cakeName}")

            if (request == null || request.cakeName.isEmpty()) {
                responseObserver?.onNext(makeOrderCakeErrorResponse(OrderCakeResponse.EnumOrderCakeStatus.INVALID_PARAMS))
            } else {
                callWithContext {
                    orderCakeController.createOrder(request.cakeName) { id, cookingTime ->
                        responseObserver?.onNext(makeOrderCakeResponse(id, cookingTime))
                        responseObserver?.onCompleted()
                    }
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
}