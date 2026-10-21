package com.example.tp3_parcial_grupo_1.ui.screens.auth

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Test

class AuthValidationTest {
    @Test
    fun `valid email has no error`() {
        assertNull(validateEmail("alex.morgan@email.com"))
    }

    @Test
    fun `blank email is required`() {
        assertEquals(com.example.tp3_parcial_grupo_1.ui.strings.AuthStrings.EmailRequired, validateEmail("  "))
    }

    @Test
    fun `email without domain is invalid`() {
        assertNotNull(validateEmail("alex@"))
        assertNotNull(validateEmail("alex.email.com"))
    }

    @Test
    fun `password shorter than minimum is rejected`() {
        assertNotNull(validatePassword("1234567"))
        assertNull(validatePassword("12345678"))
    }

    @Test
    fun `empty password is required`() {
        assertEquals(com.example.tp3_parcial_grupo_1.ui.strings.AuthStrings.PasswordRequired, validatePassword(""))
    }

    @Test
    fun `blank name is required`() {
        assertNotNull(validateFullName(" "))
        assertNull(validateFullName("Alex Morgan"))
    }
}
