package com.akvelon.orderservice.grpc

import com.akvelon.ordercakeproto.OrderCakeRequest
import com.akvelon.ordercakeproto.OrderCakeResponse
import com.akvelon.ordercakeproto.OrderCakeServiceGrpc
import com.akvelon.orderservice.dal.controller.OrderCakeController
import io.grpc.stub.StreamObserver
import org.lognet.springboot.grpc.GRpcService

@GRpcService
class OrderCakeService(val orderCakeController: OrderCakeController) : OrderCakeServiceGrpc.OrderCakeServiceImplBase() {

    override fun orderCake(request: OrderCakeRequest?, responseObserver: StreamObserver<OrderCakeResponse>?) {
        try {
            if (request == null || request.cakeName.isEmpty()) {
                responseObserver?.onNext(makeOrderCakeResponse(OrderCakeResponse.EnumOrderCakeStatus.INVALID_PARAMS))
            } else {
                orderCakeController.getCakeByName(request.cakeName)
                responseObserver?.onNext(makeOrderCakeResponse(OrderCakeResponse.EnumOrderCakeStatus.OK))
            }
        } catch (e: Exception) {
            responseObserver?.onNext(makeOrderCakeResponse(OrderCakeResponse.EnumOrderCakeStatus.UNKNOWN_ERROR))
        } finally {
            responseObserver?.onCompleted()
        }
    }

    private fun makeOrderCakeResponse(status: OrderCakeResponse.EnumOrderCakeStatus): OrderCakeResponse =
            OrderCakeResponse
                    .newBuilder()
                    .setStatus(status)
                    .build()
}