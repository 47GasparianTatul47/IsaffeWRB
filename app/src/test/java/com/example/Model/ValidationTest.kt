package com.example.Model

import com.google.common.truth.Truth.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4



@RunWith(JUnit4::class)
class ValidationTest {

    @Test
    fun whenInputIsValid() {
        val email = "qfwy9diqyiuydq"
        val password = "5727483212"
        val result = Validation.validateInput(email, password)
        assertThat(result).isEqualTo(true)
    }

}