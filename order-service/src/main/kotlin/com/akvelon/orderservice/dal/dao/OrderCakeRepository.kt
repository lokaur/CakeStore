package com.akvelon.orderservice.dal.dao

import org.apache.ibatis.annotations.*

@Mapper
interface OrderCakeRepository {
    // Using @Select here instead of @Insert since mybatis cannot return Id of the inserted row by @Insert annotation
    @Select("INSERT INTO public.\"Order\"(\"CakeId\", \"StatusId\") VALUES(#{cakeId}, #{statusId}) RETURNING \"Id\"")
    fun addOrder(cakeId: Int, statusId: Int): Int

    @Select("SELECT \"StatusId\" FROM public.\"Order\" WHERE \"Id\" = #{orderId}")
    fun getOrderStatus(orderId: Int): Int?

    @Update("UPDATE public.\"Order\" SET \"StatusId\"=#{statusId} WHERE \"Id\" = #{orderId}")
    fun updateOrderStatus(orderId: Int, statusId: Int)
}