package com.example.tp3_parcial_grupo_1.data.repository

import com.example.tp3_parcial_grupo_1.data.demo.ProfileDemoData
import com.example.tp3_parcial_grupo_1.data.local.InMemoryProfileStorage
import com.example.tp3_parcial_grupo_1.model.CoachingStyle
import com.example.tp3_parcial_grupo_1.model.NutritionPriority
import com.example.tp3_parcial_grupo_1.model.PrimaryGoal
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ProfileRepositoryTest {

    private val storage = InMemoryProfileStorage()
    private val dispatcher = StandardTestDispatcher()
    private val scope = TestScope(dispatcher)

    private fun newRepository() = ProfileRepository(storage, dispatcher)

    @Test
    fun `given nothing saved, when created, then it starts with the demo data`() = scope.runTest {
        val repository = newRepository()
        advanceUntilIdle()

        assertEquals(ProfileDemoData.profile, repository.profile.value)
        assertEquals(ProfileDemoData.nutritionPreferences, repository.nutritionPreferences.value)
        assertEquals(ProfileDemoData.voiceSettings, repository.voiceSettings.value)
    }

    @Test
    fun `given edits, when the app is opened again, then profile, nutrition and voice are restored`() = scope.runTest {
        val first = newRepository()
        first.update { it.copy(goals = setOf(PrimaryGoal.LoseBodyFat), coachingStyle = CoachingStyle.Direct, trainingDaysPerWeek = 6) }
        first.updateNutritionPreferences { it.copy(priorities = setOf(NutritionPriority.EatMoreConsistently), mealsPerDay = "4 meals") }
        first.updateVoiceSettings { it.copy(speakResponses = false) }
        advanceUntilIdle()

        val second = newRepository()
        advanceUntilIdle()

        assertEquals(setOf(PrimaryGoal.LoseBodyFat), second.profile.value.goals)
        assertEquals(CoachingStyle.Direct, second.profile.value.coachingStyle)
        assertEquals(6, second.profile.value.trainingDaysPerWeek)
        assertEquals(setOf(NutritionPriority.EatMoreConsistently), second.nutritionPreferences.value.priorities)
        assertEquals("4 meals", second.nutritionPreferences.value.mealsPerDay)
        assertEquals(false, second.voiceSettings.value.speakResponses)
    }

    @Test
    fun `given a late read, when the user already edited, then the edit is kept`() = scope.runTest {
        val saved = newRepository()
        saved.update { it.copy(trainingDaysPerWeek = 3) }
        advanceUntilIdle()

        val repository = newRepository()
        repository.update { it.copy(trainingDaysPerWeek = 5) }
        advanceUntilIdle()

        assertEquals(5, repository.profile.value.trainingDaysPerWeek)
    }

    @Test
    fun `given unreadable saved data, when created, then it falls back to the demo data`() = scope.runTest {
        storage.write("profile_v1", "not json")

        val repository = newRepository()
        advanceUntilIdle()

        assertEquals(ProfileDemoData.profile, repository.profile.value)
    }
}
