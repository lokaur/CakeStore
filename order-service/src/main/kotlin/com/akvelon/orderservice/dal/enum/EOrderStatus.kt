package com.akvelon.orderservice.dal.enum

enum class EOrderStatus(val statusId: Int) {
    NEW(1),
    IN_PROGRESS(2),
    DONE(3)
}