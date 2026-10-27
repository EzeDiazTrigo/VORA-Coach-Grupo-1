package com.example.tp3_parcial_grupo_1.ui.screens.workout

import androidx.lifecycle.SavedStateHandle
import com.example.tp3_parcial_grupo_1.data.repository.WorkoutRepository
import com.example.tp3_parcial_grupo_1.model.Exercise
import com.example.tp3_parcial_grupo_1.model.Workout
import com.example.tp3_parcial_grupo_1.ui.strings.WorkoutStrings
import java.io.IOException
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
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
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

    @Test
    fun `given no network and a detail never downloaded, when refreshing, then it is unavailable offline`() {
        fakeRepository.networkDown = true
        val neverDownloaded = WorkoutDetailViewModel(fakeRepository, SavedStateHandle(mapOf("workoutId" to "w99")))

        val state = neverDownloaded.uiState.value
        assertTrue(state.isUnavailableOffline)
        assertNull(state.errorMessage)
        assertNull(state.workout)
        assertFalse(state.isLoading)
    }

    @Test
    fun `given no network and a detail already downloaded, when refreshing, then the cached detail stays visible`() {
        fakeRepository.networkDown = true
        viewModel.refreshWorkoutDetail()

        val state = viewModel.uiState.value
        assertFalse(state.isUnavailableOffline)
        assertEquals(WorkoutStrings.ErrorGeneric, state.errorMessage)
        assertEquals("w01", state.workout?.id)
    }

    @Test
    fun `given a server error and no cached detail, when refreshing, then it is a generic error and not an offline one`() {
        fakeRepository.shouldFail = true
        val neverDownloaded = WorkoutDetailViewModel(fakeRepository, SavedStateHandle(mapOf("workoutId" to "w99")))

        val state = neverDownloaded.uiState.value
        assertFalse(state.isUnavailableOffline)
        assertEquals(WorkoutStrings.ErrorGeneric, state.errorMessage)
    }

    private class FakeWorkoutRepository : WorkoutRepository {
        var shouldFail = false
        var networkDown = false
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
            if (networkDown) return Result.failure(IOException("Unable to resolve host"))
            return if (shouldFail) Result.failure(Exception("Detail error")) else Result.success(Unit)
        }
    }
}
