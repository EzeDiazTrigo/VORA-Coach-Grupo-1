package com.example.tp3_parcial_grupo_1.ui.screens.auth

import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test
import com.example.tp3_parcial_grupo_1.ui.strings.AuthStrings

class LoginViewModelTest {
    private val viewModel = LoginViewModel()

    @Test
    fun `invalid fields show errors and do not sign in`() {
        viewModel.onSignInClick()

        assertEquals(AuthStrings.EmailRequired, viewModel.uiState.value.emailError)
        assertEquals(AuthStrings.PasswordRequired, viewModel.uiState.value.passwordError)
        assertFalse(viewModel.uiState.value.isSignedIn)
    }

    @Test
    fun `valid credentials sign in`() {
        viewModel.onEmailChange("alex.morgan@email.com")
        viewModel.onPasswordChange("12345678")

        viewModel.onSignInClick()

        assertTrue(viewModel.uiState.value.isSignedIn)
    }

    @Test
    fun `editing a field clears its error`() {
        viewModel.onSignInClick()
        viewModel.onEmailChange("a")

        assertEquals(null, viewModel.uiState.value.emailError)
    }
}
