package com.example.tp3_parcial_grupo_1.ui.screens.workout

import com.example.tp3_parcial_grupo_1.data.repository.WorkoutRepository
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
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class WorkoutListViewModelTest {

    private val testDispatcher = UnconfinedTestDispatcher()
    private lateinit var fakeRepository: FakeWorkoutRepository
    private lateinit var viewModel: WorkoutListViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        fakeRepository = FakeWorkoutRepository()
        viewModel = WorkoutListViewModel(fakeRepository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `initial state collects workouts and sets loading to false`() {
        val state = viewModel.uiState.value
        assertFalse(state.isLoading)
        assertEquals(1, state.workouts.size)
        assertEquals("w01", state.workouts.first().id)
    }

    @Test
    fun `setFilter updates selected level filter`() {
        viewModel.setFilter(LevelFilter.INTERMEDIATE)
        assertEquals(LevelFilter.INTERMEDIATE, viewModel.uiState.value.selectedFilter)
    }

    @Test
    fun `refreshWorkouts handles error and updates errorMessage`() {
        fakeRepository.shouldFail = true
        viewModel.refreshWorkouts()

        assertEquals(WorkoutStrings.ErrorGeneric, viewModel.uiState.value.errorMessage)
        assertFalse(viewModel.uiState.value.isLoading)
    }

    private class FakeWorkoutRepository : WorkoutRepository {
        var shouldFail = false
        private val workoutsFlow = MutableStateFlow(
            listOf(
                Workout("w01", "Fuerza", "Intermedio", 45, 3, "Desc")
            )
        )

        override fun getWorkouts(): Flow<List<Workout>> = workoutsFlow

        override fun getWorkoutById(id: String): Flow<Workout?> =
            MutableStateFlow(workoutsFlow.value.find { it.id == id })

        override suspend fun refreshWorkouts(): Result<Unit> {
            return if (shouldFail) Result.failure(Exception("Network error")) else Result.success(Unit)
        }

        override suspend fun refreshWorkoutDetails(id: String): Result<Unit> {
            return if (shouldFail) Result.failure(Exception("Network error")) else Result.success(Unit)
        }
    }
}
