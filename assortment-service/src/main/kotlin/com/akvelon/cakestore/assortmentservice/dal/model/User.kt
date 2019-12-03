package com.akvelon.cakestore.assortmentservice.dal.model

class User(val id: Int) {
    val name: String = ""
        get() {
            return field.trim()
        }

    val password: String = ""
        get() {
            return field.trim()
        }
}