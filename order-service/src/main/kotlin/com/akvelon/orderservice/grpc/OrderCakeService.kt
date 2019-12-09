package com.akvelon.orderservice.grpc

import com.akvelon.orderservice.dal.controller.OrderCakeController
import io.grpc.stub.StreamObserver
import com.akvelon.ordercakeproto.*
import org.lognet.springboot.grpc.GRpcService

@GRpcService
class OrderCakeService(val orderCakeController: OrderCakeController) : OrderCakeServiceGrpc.OrderCakeServiceImplBase() {

    override fun orderCake(request: OrderCakeRequest?, responseObserver: StreamObserver<OrderCakeResponse>?) {
        try {
            if (request == null || request.cakeName.isEmpty()) {
                responseObserver?.onNext(makeOrderCakeResponse(OrderCakeResponse.EnumOrderCakeStatus.INVALID_PARAMS))
            } else {
//                orderCakeController.createOrder()
//                responseObserver?.onNext(makeAddCakeResponse(AddCakeResponse.EnumAddCakeStatus.OK))
            }
//        } catch (e: EntityAlreadyExistsException) {
//            responseObserver?.onNext(makeAddCakeResponse(AddCakeResponse.EnumAddCakeStatus.CAKE_ALREADY_EXIST))
        } catch (e: Exception) {
//            responseObserver?.onNext(makeAddCakeResponse(AddCakeResponse.EnumAddCakeStatus.UNKNOWN_ERROR))
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