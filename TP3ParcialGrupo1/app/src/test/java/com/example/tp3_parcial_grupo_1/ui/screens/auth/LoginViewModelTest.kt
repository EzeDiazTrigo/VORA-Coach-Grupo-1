package com.example.tp3_parcial_grupo_1.ui.screens.auth

import com.example.tp3_parcial_grupo_1.data.repository.AuthRepository
import com.example.tp3_parcial_grupo_1.ui.strings.AuthStrings
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class LoginViewModelTest {
    private val dispatcher = StandardTestDispatcher()

    private class FakeAuthRepository(private val result: Result<Unit>) : AuthRepository {
        var signInCalls = 0
        override suspend fun signIn(email: String, password: String): Result<Unit> {
            signInCalls++
            return result
        }
        override suspend fun signUp(fullName: String, email: String, password: String) = result
    }

    @Before
    fun setUp() = Dispatchers.setMain(dispatcher)

    @After
    fun tearDown() = Dispatchers.resetMain()

    @Test
    fun `invalid fields show errors and do not call repository`() = runTest(dispatcher) {
        val repository = FakeAuthRepository(Result.success(Unit))
        val viewModel = LoginViewModel(repository)

        viewModel.onSignInClick()
        advanceUntilIdle()

        assertEquals(AuthStrings.EmailRequired, viewModel.uiState.value.emailError)
        assertEquals(AuthStrings.PasswordRequired, viewModel.uiState.value.passwordError)
        assertEquals(0, repository.signInCalls)
    }

    @Test
    fun `valid credentials sign in successfully`() = runTest(dispatcher) {
        val viewModel = LoginViewModel(FakeAuthRepository(Result.success(Unit)))
        viewModel.onEmailChange("alex.morgan@email.com")
        viewModel.onPasswordChange("12345678")

        viewModel.onSignInClick()
        assertTrue(viewModel.uiState.value.isLoading)
        advanceUntilIdle()

        assertTrue(viewModel.uiState.value.isSignedIn)
        assertFalse(viewModel.uiState.value.isLoading)
    }

    @Test
    fun `repository failure shows a message`() = runTest(dispatcher) {
        val viewModel = LoginViewModel(FakeAuthRepository(Result.failure(IllegalStateException())))
        viewModel.onEmailChange("alex.morgan@email.com")
        viewModel.onPasswordChange("12345678")

        viewModel.onSignInClick()
        advanceUntilIdle()

        assertEquals(AuthStrings.SignInFailed, viewModel.uiState.value.message)
        assertFalse(viewModel.uiState.value.isSignedIn)
        viewModel.onMessageShown()
        assertNull(viewModel.uiState.value.message)
    }
}
