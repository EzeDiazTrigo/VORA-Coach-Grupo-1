package com.example.tp3_parcial_grupo_1.ui.screens.profile

import androidx.lifecycle.ViewModel
import com.example.tp3_parcial_grupo_1.data.repository.ProfileRepository
import com.example.tp3_parcial_grupo_1.ui.strings.Strings
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class EditProfileViewModel @Inject constructor(
    private val repository: ProfileRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(repository.profile.value.toEditUiState())
    val uiState: StateFlow<EditProfileUiState> = _uiState.asStateFlow()

    fun onFirstNameChange(value: String) = _uiState.update { it.copy(firstName = value, firstNameError = null) }

    fun onLastNameChange(value: String) = _uiState.update { it.copy(lastName = value, lastNameError = null) }

    fun onDateOfBirthChange(value: String) = _uiState.update { it.copy(dateOfBirth = value, dateOfBirthError = null) }

    fun onHeightChange(value: String) = _uiState.update { it.copy(height = value, heightError = null) }

    fun onWeightChange(value: String) = _uiState.update { it.copy(weight = value, weightError = null) }

    fun onGenderChange(value: String) = _uiState.update { it.copy(gender = value) }

    fun onChangePhotoClick() = _uiState.update { it.copy(message = Strings.DemoMode) }

    fun onMessageShown() = _uiState.update { it.copy(message = null) }

    fun onSaveClick() {
        val state = _uiState.value
        val validated = state.copy(
            firstNameError = validateFirstName(state.firstName),
            lastNameError = validateLastName(state.lastName),
            dateOfBirthError = validateDateOfBirth(state.dateOfBirth),
            heightError = validateHeight(state.height),
            weightError = validateWeight(state.weight)
        )
        val hasErrors = listOf(
            validated.firstNameError, validated.lastNameError, validated.dateOfBirthError,
            validated.heightError, validated.weightError
        ).any { it != null }
        if (hasErrors) {
            _uiState.value = validated
            return
        }
        repository.update {
            it.copy(
                firstName = state.firstName.trim(),
                lastName = state.lastName.trim(),
                dateOfBirth = state.dateOfBirth.trim(),
                heightCm = state.height.trim().toInt(),
                weightKg = state.weight.trim().toInt(),
                gender = state.gender
            )
        }
        _uiState.value = validated.copy(isSaved = true)
    }
}
