package com.akvelon.orderservice.dal.dao

import org.apache.ibatis.annotations.Insert
import org.apache.ibatis.annotations.Mapper
import org.apache.ibatis.annotations.Update

@Mapper
interface OrderCakeRepository {
    @Insert("INSERT INTO public.\"Order\"(\"CakeId\", \"StatusId\") VALUES(#{cakeId}, #{statusId})")
    fun addOrder(cakeId: Int, statusId: Int)

    @Update("UPDATE public.\"Order\" SET \"StatusId\"=#{statusId} WHERE \"Id\" = #{orderId}")
    fun updateOrderStatus(orderId: Int, statusId: Int)
}