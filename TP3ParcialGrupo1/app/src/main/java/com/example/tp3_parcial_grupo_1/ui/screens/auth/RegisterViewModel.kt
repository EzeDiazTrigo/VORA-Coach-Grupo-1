package com.example.tp3_parcial_grupo_1.ui.screens.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tp3_parcial_grupo_1.data.repository.AuthRepository
import com.example.tp3_parcial_grupo_1.ui.strings.AuthStrings
import com.example.tp3_parcial_grupo_1.ui.strings.Strings
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(RegisterUiState())
    val uiState: StateFlow<RegisterUiState> = _uiState.asStateFlow()

    fun onFullNameChange(value: String) = _uiState.update { it.copy(fullName = value, fullNameError = null) }

    fun onEmailChange(value: String) = _uiState.update { it.copy(email = value, emailError = null) }

    fun onPasswordChange(value: String) = _uiState.update { it.copy(password = value, passwordError = null) }

    fun onSignUpClick() {
        val current = _uiState.value
        if (current.isLoading) return
        val nameError = validateFullName(current.fullName)
        val emailError = validateEmail(current.email)
        val passwordError = validatePassword(current.password)
        if (nameError != null || emailError != null || passwordError != null) {
            _uiState.update {
                it.copy(fullNameError = nameError, emailError = emailError, passwordError = passwordError)
            }
            return
        }
        _uiState.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            authRepository.signUp(current.fullName.trim(), current.email.trim(), current.password)
                .onSuccess { _uiState.update { it.copy(isLoading = false, isSignedUp = true) } }
                .onFailure { _uiState.update { it.copy(isLoading = false, message = AuthStrings.SignUpFailed) } }
        }
    }

    fun onDemoNoticeClick() = _uiState.update { it.copy(message = Strings.DemoMode) }

    fun onMessageShown() = _uiState.update { it.copy(message = null) }

    fun onSignedUpHandled() = _uiState.update { it.copy(isSignedUp = false) }
}
