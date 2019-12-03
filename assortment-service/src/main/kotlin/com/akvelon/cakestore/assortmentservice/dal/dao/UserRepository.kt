package com.akvelon.cakestore.assortmentservice.dal.dao

import com.akvelon.cakestore.assortmentservice.dal.model.User
import org.apache.ibatis.annotations.Mapper
import org.apache.ibatis.annotations.Select

@Mapper
interface UserRepository {
    @Select("SELECT * FROM public.\"User\"")
    fun findAll(): List<User>

    @Select("SELECT * FROM public.\"User\" WHERE \"Name\" = #{name}")
    fun findByName(name: String): User?
}