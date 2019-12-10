package com.akvelon.orderservice.dal.model

import com.akvelon.orderservice.dal.enum.EOrderStatus

class Order(val id: Int, val cakeId: Int, val statusId: Int, val createdAt: String) {
    val status: EOrderStatus
        get() = EOrderStatus.fromInt(statusId)!!
}