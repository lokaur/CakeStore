package com.akvelon.cakestore.assortmentservice.grpc

import io.grpc.stub.StreamObserver
import org.baeldung.grpc.CakesAssortmentRequest
import org.baeldung.grpc.CakesAssortmentResponse
import org.baeldung.grpc.CakesAssortmentServiceGrpc
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
}