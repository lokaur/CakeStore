package com.akvelon.cakestore.assortmentservice.grpc

import com.akvelon.cakestore.assortmentservice.dal.controller.CakeController
import com.akvelon.cakestore.assortmentservice.dal.model.Cake
import com.akvelon.cakestore.assortmentservice.exceptions.EntityAlreadyExistsException
import com.akvelon.cakestore.assortmentservice.exceptions.EntityNotExist
import com.akvelon.cakestore.assortmentservice.exceptions.EntityRemoveException
import io.grpc.stub.StreamObserver
import org.baeldung.grpc.*
import org.lognet.springboot.grpc.GRpcService
import org.springframework.beans.factory.annotation.Autowired

@GRpcService
class CakesAssortmentService(@Autowired val cakeController: CakeController)
    : CakesAssortmentServiceGrpc.CakesAssortmentServiceImplBase() {

    override fun addCake(request: AddCakeRequest?, responseObserver: StreamObserver<AddCakeResponse>?) {
        try {
            if (request == null || request.name.isEmpty()) {
                responseObserver?.onNext(makeAddCakeResponse(AddCakeResponse.EnumAddCakeStatus.INVALID_PARAMS))
            } else {
                cakeController.addCake(Cake(0, request.name, request.price, request.cookingTime))
                responseObserver?.onNext(makeAddCakeResponse(AddCakeResponse.EnumAddCakeStatus.OK))
            }
        } catch (e: EntityAlreadyExistsException) {
            responseObserver?.onNext(makeAddCakeResponse(AddCakeResponse.EnumAddCakeStatus.CAKE_ALREADY_EXIST))
        } catch (e: Exception) {
            responseObserver?.onNext(makeAddCakeResponse(AddCakeResponse.EnumAddCakeStatus.UNKNOWN_ERROR))
        } finally {
            responseObserver?.onCompleted()
        }
    }

    private fun makeAddCakeResponse(status: AddCakeResponse.EnumAddCakeStatus): AddCakeResponse =
            AddCakeResponse
                    .newBuilder()
                    .setStatus(status)
                    .build()

    override fun removeCake(request: RemoveCakeRequest?, responseObserver: StreamObserver<RemoveCakeResponse>?) {
        try {
            if (request == null || request.name.isEmpty()) {
                responseObserver?.onNext(makeRemoveCakeResponse(RemoveCakeResponse.EnumRemoveCakeStatus.INVALID_PARAMS))
            } else {
                cakeController.removeCakeByName(request.name)
                responseObserver?.onNext(makeRemoveCakeResponse(RemoveCakeResponse.EnumRemoveCakeStatus.OK))
            }
        } catch (e: EntityRemoveException) {
            responseObserver?.onNext(makeRemoveCakeResponse(RemoveCakeResponse.EnumRemoveCakeStatus.CAKE_NOT_FOUND))
        } catch (e: Exception) {
            responseObserver?.onNext(makeRemoveCakeResponse(RemoveCakeResponse.EnumRemoveCakeStatus.UNKNOWN_ERROR))
        } finally {
            responseObserver?.onCompleted()
        }
    }

    private fun makeRemoveCakeResponse(status: RemoveCakeResponse.EnumRemoveCakeStatus): RemoveCakeResponse =
            RemoveCakeResponse
                    .newBuilder()
                    .setStatus(status)
                    .build()

    override fun getCake(request: GetCakeRequest?, responseObserver: StreamObserver<GetCakeResponse>?) {
        try {
            if (request == null || request.name.isEmpty()) {
                responseObserver?.onNext(makeGetCakeErrorResponse(GetCakeResponse.EnumGetCakeStatus.INVALID_PARAMS))
            } else {
                val cake = cakeController.getCakeByName(request.name)!!
                responseObserver?.onNext(makeGetCakeSuccessResponse(cake))
            }
        } catch (e: EntityNotExist) {
            responseObserver?.onNext(makeGetCakeErrorResponse(GetCakeResponse.EnumGetCakeStatus.CAKE_NOT_FOUND))
        } catch (e: Exception) {
            responseObserver?.onNext(makeGetCakeErrorResponse(GetCakeResponse.EnumGetCakeStatus.UNKNOWN_ERROR))
        } finally {
            responseObserver?.onCompleted()
        }
    }

    private fun makeGetCakeSuccessResponse(cake: Cake): GetCakeResponse =
            GetCakeResponse
                    .newBuilder()
                    .setStatus(GetCakeResponse.EnumGetCakeStatus.OK)
                    .setName(cake.name)
                    .setPrice(cake.price)
                    .setCookingTime(cake.cookingTime)
                    .build()

    private fun makeGetCakeErrorResponse(status: GetCakeResponse.EnumGetCakeStatus): GetCakeResponse =
            GetCakeResponse
                    .newBuilder()
                    .setStatus(status)
                    .build()
}