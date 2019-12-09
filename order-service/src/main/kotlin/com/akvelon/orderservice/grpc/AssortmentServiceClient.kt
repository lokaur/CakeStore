package com.akvelon.orderservice.grpc

import com.akvelon.cakesassortmentproto.CakesAssortmentServiceGrpc
import com.akvelon.cakesassortmentproto.GetCakeRequest
import com.netflix.appinfo.InstanceInfo
import com.netflix.discovery.EurekaClient
import io.grpc.ManagedChannel
import io.grpc.ManagedChannelBuilder
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class AssortmentServiceClient(@Autowired val client: EurekaClient) {
    fun getCakeByName(name: String) {
        val instanceInfo: InstanceInfo = client.getNextServerFromEureka("CakesAssortmentService", false)
        val channel: ManagedChannel = ManagedChannelBuilder.forAddress(instanceInfo.ipAddr, instanceInfo.port)
                .usePlaintext()
                .build()

        val stub: CakesAssortmentServiceGrpc.CakesAssortmentServiceFutureStub = CakesAssortmentServiceGrpc.newFutureStub(channel)
        val response = stub.getCake(GetCakeRequest.newBuilder().setName(name).build())
    }
}