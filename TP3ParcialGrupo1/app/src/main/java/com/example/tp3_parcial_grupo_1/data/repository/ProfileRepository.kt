package com.example.tp3_parcial_grupo_1.data.repository

import com.example.tp3_parcial_grupo_1.data.demo.ProfileDemoData
import com.example.tp3_parcial_grupo_1.model.NutritionPreferences
import com.example.tp3_parcial_grupo_1.model.UserProfile
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject
import javax.inject.Singleton

// Perfil de demostración en memoria: lo comparten el Dashboard y las pantallas de edición.
@Singleton
class ProfileRepository @Inject constructor() {

    private val _profile = MutableStateFlow(ProfileDemoData.profile)
    val profile: StateFlow<UserProfile> = _profile.asStateFlow()

    fun update(transform: (UserProfile) -> UserProfile) = _profile.update(transform)

    private val _nutritionPreferences = MutableStateFlow(ProfileDemoData.nutritionPreferences)
    val nutritionPreferences: StateFlow<NutritionPreferences> = _nutritionPreferences.asStateFlow()

    fun updateNutritionPreferences(transform: (NutritionPreferences) -> NutritionPreferences) =
        _nutritionPreferences.update(transform)
}
