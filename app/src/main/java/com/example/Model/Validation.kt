package com.example.Model

object Validation {

    fun validateInput(email: String, password: String): Boolean {


        return !(email.isEmpty() || password.isEmpty() || password.length <= 7)

    }

}