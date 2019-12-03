package com.akvelon.cakestore.assortmentservice.grpc

import io.grpc.stub.StreamObserver
import org.baeldung.grpc.*
import org.lognet.springboot.grpc.GRpcService

@GRpcService
class CakesAssortmentService : CakesAssortmentServiceGrpc.CakesAssortmentServiceImplBase() {
    override fun hello(request: CakesAssortmentRequest?, responseObserver: StreamObserver<CakesAssortmentResponse>?) {
        println("Received: $request")
        responseObserver?.onNext(
                CakesAssortmentResponse
                        .newBuilder()
                        .setGreeting("Hello, ${request?.firstName} ${request?.lastName}")
                        .build())
        responseObserver?.onCompleted()
    }

    override fun addCake(request: AddCakeRequest?, responseObserver: StreamObserver<AddCakeResponse>?) {
        println("addCakeRequest: ${request?.name}")
        responseObserver?.onCompleted()
    }

    override fun removeCake(request: RemoveCakeRequest?, responseObserver: StreamObserver<RemoveCakeResponse>?) {
        super.removeCake(request, responseObserver)
    }
}