package com.example.tp3_parcial_grupo_1.ui.screens.workout

import androidx.lifecycle.SavedStateHandle
import com.example.tp3_parcial_grupo_1.data.repository.WorkoutRepository
import com.example.tp3_parcial_grupo_1.model.Exercise
import com.example.tp3_parcial_grupo_1.model.Workout
import com.example.tp3_parcial_grupo_1.ui.strings.WorkoutStrings
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class WorkoutDetailViewModelTest {

    private val testDispatcher = UnconfinedTestDispatcher()
    private lateinit var fakeRepository: FakeWorkoutRepository
    private lateinit var viewModel: WorkoutDetailViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        fakeRepository = FakeWorkoutRepository()
        val savedStateHandle = SavedStateHandle(mapOf("workoutId" to "w01"))
        viewModel = WorkoutDetailViewModel(fakeRepository, savedStateHandle)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `initial state collects workout detail correctly`() {
        val state = viewModel.uiState.value
        assertFalse(state.isLoading)
        assertNotNull(state.workout)
        assertEquals("w01", state.workout?.id)
        assertEquals(1, state.workout?.exercises?.size)
    }

    @Test
    fun `refreshWorkoutDetail handles failure`() {
        fakeRepository.shouldFail = true
        viewModel.refreshWorkoutDetail()

        assertEquals(WorkoutStrings.ErrorGeneric, viewModel.uiState.value.errorMessage)
        assertFalse(viewModel.uiState.value.isLoading)
    }

    private class FakeWorkoutRepository : WorkoutRepository {
        var shouldFail = false
        private val workout = Workout(
            id = "w01",
            name = "Fuerza",
            level = "Intermedio",
            durationMinutes = 45,
            exerciseCount = 1,
            description = "Desc",
            exercises = listOf(
                Exercise("e01", "Sentadilla", "Piernas", 4, 10, 60, "Instrucciones", "Tip")
            )
        )

        override fun getWorkouts(): Flow<List<Workout>> = MutableStateFlow(listOf(workout))

        override fun getWorkoutById(id: String): Flow<Workout?> = MutableStateFlow(workout.takeIf { it.id == id })

        override suspend fun refreshWorkouts(): Result<Unit> {
            return if (shouldFail) Result.failure(Exception("Detail error")) else Result.success(Unit)
        }

        override suspend fun refreshWorkoutDetails(id: String): Result<Unit> {
            return if (shouldFail) Result.failure(Exception("Detail error")) else Result.success(Unit)
        }
    }
}
