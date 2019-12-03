package com.akvelon.cakestore.assortmentservice.dal.controller

import com.akvelon.cakestore.assortmentservice.dal.dao.CakeRepository
import com.akvelon.cakestore.assortmentservice.dal.model.Cake
import com.akvelon.cakestore.assortmentservice.exceptions.EntityAlreadyExistsException
import com.akvelon.cakestore.assortmentservice.exceptions.EntityInsertException
import com.akvelon.cakestore.assortmentservice.exceptions.EntityRemoveException

class CakeController(val cakeRepository: CakeRepository) {
    fun addCake(cake: Cake) {
        val existingCake = cakeRepository.getCakeByName(cake.name)

        if (existingCake != null) {
            throw EntityAlreadyExistsException()
        }

        val result = cakeRepository.addCake(cake.name, cake.price, cake.cookingTime)

        if (result != 1) {
            throw EntityInsertException()
        }
    }

    fun removeCakeByName(name: String) {
        val result = cakeRepository.removeCakeByName(name)

        if (result != 1) {
            throw EntityRemoveException()
        }
    }
}