package com.akvelon.orderservice.grpc

import com.akvelon.cakesassortmentproto.CakesAssortmentServiceGrpc
import com.akvelon.cakesassortmentproto.GetCakeRequest
import com.akvelon.cakesassortmentproto.GetCakeResponse
import com.google.common.util.concurrent.FutureCallback
import com.google.common.util.concurrent.Futures
import com.google.common.util.concurrent.MoreExecutors.directExecutor
import com.netflix.appinfo.InstanceInfo
import com.netflix.discovery.EurekaClient
import io.grpc.ManagedChannel
import io.grpc.ManagedChannelBuilder
import io.grpc.Status
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class AssortmentServiceClient(@Autowired val client: EurekaClient) {

    private val logger: Logger = LoggerFactory.getLogger(AssortmentServiceClient::class.toString())

    fun getCakeByName(name: String, callback: (GetCakeResponse?) -> Unit) = getCake(GetCakeRequest.newBuilder().setName(name).build(), callback)

    fun getCakeById(id: Int, callback: (GetCakeResponse?) -> Unit) = getCake(GetCakeRequest.newBuilder().setId(id).build(), callback)

    private fun getCake(getCakeRequest: GetCakeRequest, callback: (GetCakeResponse?) -> Unit) {
        val instanceInfo: InstanceInfo = client.getNextServerFromEureka("CakesAssortmentService", false)
        val channel: ManagedChannel = ManagedChannelBuilder.forAddress(instanceInfo.ipAddr, instanceInfo.port)
                .usePlaintext()
                .build()

        val response = CakesAssortmentServiceGrpc.newFutureStub(channel).getCake(getCakeRequest)

        Futures.addCallback(
                response,
                object : FutureCallback<GetCakeResponse> {
                    override fun onSuccess(result: GetCakeResponse?) {
                        if (result == null || result.status != GetCakeResponse.EnumGetCakeStatus.OK) {
                            logger.error("GetCake failed: ${result?.status}")
                            callback(null)
                            return
                        }

                        logger.info("GetCake success: ${result.name}; ${result.cookingTime}")
                        callback(result)
                    }

                    override fun onFailure(t: Throwable?) {
                        val status: Status = Status.fromThrowable(t)
                        logger.error("GetCake request failed: ${status.code}; ${status.description}")
                        callback(null)
                    }
                },
                directExecutor())
    }
}