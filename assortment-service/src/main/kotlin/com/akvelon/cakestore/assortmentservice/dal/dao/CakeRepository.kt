package com.akvelon.cakestore.assortmentservice.dal.dao

import com.akvelon.cakestore.assortmentservice.dal.model.Cake
import org.apache.ibatis.annotations.Delete
import org.apache.ibatis.annotations.Insert
import org.apache.ibatis.annotations.Mapper
import org.apache.ibatis.annotations.Select

@Mapper
interface CakeRepository {
    @Insert("INSERT INTO public.\"Cake\"(\"Name\", \"Price\", \"CookingTime\") VALUES(#{name}, #{price}, #{cookingTime})")
    fun addCake(name: String, price: Int, cookingTime: Int): Int

    @Select("SELECT * FROM public.\"Cake\" WHERE \"Id\" = #{id}")
    fun getCakeById(id: Int): Cake?

    @Select("SELECT * FROM public.\"Cake\" WHERE \"Name\" = #{name}")
    fun getCakeByName(name: String): Cake?

    @Delete("DELETE FROM public.\"Cake\" WHERE \"Name\" = #{name}")
    fun removeCakeByName(name: String): Int
}