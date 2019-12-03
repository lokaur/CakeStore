package com.akvelon.cakestore.assortmentservice.grpc.interceptors

import com.akvelon.cakestore.assortmentservice.dal.dao.UserRepository
import io.grpc.*
import org.lognet.springboot.grpc.GRpcGlobalInterceptor
import org.springframework.core.annotation.Order

@GRpcGlobalInterceptor
@Order(20)
class AuthenticationInterceptor(private val userRepository: UserRepository) : ServerInterceptor {
    override fun <ReqT : Any?, RespT : Any?> interceptCall(call: ServerCall<ReqT, RespT>?, headers: Metadata?, next: ServerCallHandler<ReqT, RespT>?): ServerCall.Listener<ReqT> {
        val userName = headers?.get(Metadata.Key.of("Login", Metadata.ASCII_STRING_MARSHALLER)) ?: ""
        val password = headers?.get(Metadata.Key.of("Password", Metadata.ASCII_STRING_MARSHALLER)) ?: ""

        // Stub authorization
        if (userName.isEmpty() || password.isEmpty()) {
            throw Status.UNAUTHENTICATED.withDescription("Invalid UserName or Password").asException()
        }

        val user = userRepository.findByName(userName)
                ?: throw Status.UNAUTHENTICATED.withDescription("User $userName not found").asException()

        if (password != user.password) {
            throw Status.UNAUTHENTICATED.withDescription("Wrong password").asException()
        }

        return next!!.startCall(call, headers)
    }
}