package com.akvelon.cakestore.assortmentservice.dal.mapper

import com.akvelon.cakestore.assortmentservice.dal.model.User
import org.apache.ibatis.annotations.Mapper
import org.apache.ibatis.annotations.Select

@Mapper
interface UserMapper {
    @Select("SELECT * FROM public.Users")
    fun getAllUsers(): Array<User>
}