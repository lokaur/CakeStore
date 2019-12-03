package com.akvelon.cakestore.assortmentservice.grpc.interceptors

import io.grpc.*
import io.grpc.ForwardingServerCallListener.SimpleForwardingServerCallListener
import org.lognet.springboot.grpc.GRpcGlobalInterceptor
import org.springframework.core.annotation.Order
import org.springframework.security.core.AuthenticationException

@GRpcGlobalInterceptor
@Order(10)
class ExceptionTranslationInterceptor() : ServerInterceptor {
    override fun <ReqT : Any?, RespT : Any?> interceptCall(
            call: ServerCall<ReqT, RespT>?,
            headers: Metadata?,
            next: ServerCallHandler<ReqT, RespT>?): ServerCall.Listener<ReqT> {
        val delegate = next!!.startCall(call, headers)
        return object : SimpleForwardingServerCallListener<ReqT>(delegate) {
            override fun onHalfClose() {
                try {
                    super.onHalfClose()
                } catch (e: Exception) {
                    if (e is AuthenticationException) {
                        handleAuthenticationException(e)
                    } else {
                        throw e
                    }
                }
            }

            private fun handleAuthenticationException(exception: AuthenticationException) {
                call!!.close(Status.UNAUTHENTICATED.withDescription(exception.message)
                        .withCause(exception), Metadata())
            }
        }
    }
}