package com.example.tp3_parcial_grupo_1.ui.screens.workout

import androidx.lifecycle.SavedStateHandle
import com.example.tp3_parcial_grupo_1.data.repository.WorkoutRepository
import com.example.tp3_parcial_grupo_1.model.Exercise
import com.example.tp3_parcial_grupo_1.model.Workout
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ActiveWorkoutViewModelTest {

    private val dispatcher = StandardTestDispatcher()
    private val scope = TestScope(dispatcher)

    private val workout = Workout(
        id = "w01",
        name = "Upper Body",
        level = "Intermediate",
        durationMinutes = 42,
        exerciseCount = 2,
        exercises = listOf(
            Exercise("e01", "Bench Press", "Chest", sets = 2, reps = 10, restSeconds = 90, instructions = "", tip = ""),
            Exercise("e02", "Seated Cable Row", "Back", sets = 2, reps = 12, restSeconds = 75, instructions = "", tip = "")
        )
    )

    @Before
    fun setUp() = Dispatchers.setMain(dispatcher)

    @After
    fun tearDown() = Dispatchers.resetMain()

    private fun newViewModel() = ActiveWorkoutViewModel(FakeRepository(workout), SavedStateHandle(mapOf("workoutId" to "w01")))

    @Test
    fun `given the screen opens, when the workout loads, then it starts on the first exercise and set`() = scope.run {
        val viewModel = newViewModel()
        runCurrent()

        val state = viewModel.uiState.value
        assertEquals(workout, state.workout)
        assertEquals(0, state.exerciseIndex)
        assertEquals(0, state.activeSetIndex)
    }

    @Test
    fun `given a rest in progress, when time passes, then it counts down second by second and stops at zero`() = scope.run {
        val viewModel = newViewModel()
        runCurrent()
        val start = viewModel.uiState.value.restTimeSeconds

        advanceTimeBy(3_000)
        runCurrent()
        assertEquals(start - 3, viewModel.uiState.value.restTimeSeconds)

        advanceTimeBy((start * 1_000L) + 5_000)
        runCurrent()
        assertEquals(0, viewModel.uiState.value.restTimeSeconds)
    }

    @Test
    fun `given a rest, when adding thirty seconds or skipping, then the remaining time follows`() = scope.run {
        val viewModel = newViewModel()
        runCurrent()
        val start = viewModel.uiState.value.restTimeSeconds

        viewModel.addRestTime()
        assertEquals(start + 30, viewModel.uiState.value.restTimeSeconds)

        viewModel.skipRest()
        advanceTimeBy(5_000)
        assertEquals(0, viewModel.uiState.value.restTimeSeconds)
    }

    @Test
    fun `given a completed set, when it is not the last one, then the next set starts a rest of that exercise`() = scope.run {
        val viewModel = newViewModel()
        runCurrent()

        viewModel.completeCurrentSet(onFinished = { error("must not finish") })

        val state = viewModel.uiState.value
        assertEquals(1, state.activeSetIndex)
        assertEquals(0, state.exerciseIndex)
        assertEquals(90, state.restTimeSeconds)
    }

    @Test
    fun `given the last set of an exercise, when completing it, then the next exercise starts on its first set`() = scope.run {
        val viewModel = newViewModel()
        runCurrent()
        viewModel.completeCurrentSet {}

        viewModel.completeCurrentSet(onFinished = { error("must not finish") })

        val state = viewModel.uiState.value
        assertEquals(1, state.exerciseIndex)
        assertEquals(0, state.activeSetIndex)
    }

    @Test
    fun `given the last set of the last exercise, when completing it, then the workout finishes`() = scope.run {
        val viewModel = newViewModel()
        runCurrent()
        repeat(3) { viewModel.completeCurrentSet {} }
        var finished = false

        viewModel.completeCurrentSet { finished = true }

        assertTrue(finished)
        assertEquals(1, viewModel.uiState.value.exerciseIndex)
    }

    @Test
    fun `given the weight, when removing more than it has, then it stops at the minimum`() = scope.run {
        val viewModel = newViewModel()
        runCurrent()

        repeat(40) { viewModel.addWeight(-2.5) }

        assertEquals(2.5, viewModel.uiState.value.weightKg, 0.0)
        assertFalse(viewModel.uiState.value.weightKg < 2.5)
    }

    private class FakeRepository(private val workout: Workout) : WorkoutRepository {
        override fun getWorkouts(): Flow<List<Workout>> = MutableStateFlow(listOf(workout))
        override fun getWorkoutById(id: String): Flow<Workout?> = MutableStateFlow(workout.takeIf { it.id == id })
        override suspend fun refreshWorkouts(): Result<Unit> = Result.success(Unit)
        override suspend fun refreshWorkoutDetails(id: String): Result<Unit> = Result.success(Unit)
    }
}
