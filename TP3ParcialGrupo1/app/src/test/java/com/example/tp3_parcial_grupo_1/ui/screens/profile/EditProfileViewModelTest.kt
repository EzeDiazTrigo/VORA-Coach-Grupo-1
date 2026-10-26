package com.example.tp3_parcial_grupo_1.ui.screens.profile

import com.example.tp3_parcial_grupo_1.data.repository.ProfileRepository
import com.example.tp3_parcial_grupo_1.ui.strings.ProfileStrings
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

class EditProfileViewModelTest {
    private val repository = ProfileRepository()
    private val viewModel = EditProfileViewModel(repository)

    @Test
    fun `starts with the current profile values`() {
        val state = viewModel.uiState.value

        assertEquals("Alex", state.firstName)
        assertEquals("178", state.height)
        assertFalse(state.isSaved)
    }

    @Test
    fun `invalid fields show errors and do not save`() {
        viewModel.onFirstNameChange("")
        viewModel.onHeightChange("50")

        viewModel.onSaveClick()

        assertEquals(ProfileStrings.FirstNameRequired, viewModel.uiState.value.firstNameError)
        assertEquals(ProfileStrings.HeightInvalid, viewModel.uiState.value.heightError)
        assertFalse(viewModel.uiState.value.isSaved)
        assertEquals("Alex", repository.profile.value.firstName)
    }

    @Test
    fun `valid changes are saved to the shared profile`() {
        viewModel.onFirstNameChange(" Sam ")
        viewModel.onWeightChange("80")
        viewModel.onGenderChange("Male")

        viewModel.onSaveClick()

        val profile = repository.profile.value
        assertTrue(viewModel.uiState.value.isSaved)
        assertEquals("Sam", profile.firstName)
        assertEquals(80, profile.weightKg)
        assertEquals("Male", profile.gender)
    }

    @Test
    fun `editing a field clears its error`() {
        viewModel.onFirstNameChange("")
        viewModel.onSaveClick()

        viewModel.onFirstNameChange("A")

        assertNull(viewModel.uiState.value.firstNameError)
    }
}
