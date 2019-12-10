package com.akvelon.orderservice.dal.enum

import com.akvelon.ordercakeproto.CheckOrderStatusResponse

enum class EOrderStatus(val statusId: Int) {
    NEW(1),
    IN_PROGRESS(2),
    DONE(3);

    companion object {
        private val map = values().associateBy(EOrderStatus::statusId)
        fun fromInt(type: Int): EOrderStatus? = map[type]
        fun toEResponseOrderStatus(status: EOrderStatus) =
                when (status) {
                    IN_PROGRESS -> CheckOrderStatusResponse.EnumOrderStatus.IN_PROGRESS
                    DONE -> CheckOrderStatusResponse.EnumOrderStatus.DONE
                    else -> CheckOrderStatusResponse.EnumOrderStatus.NEW
                }
    }
}