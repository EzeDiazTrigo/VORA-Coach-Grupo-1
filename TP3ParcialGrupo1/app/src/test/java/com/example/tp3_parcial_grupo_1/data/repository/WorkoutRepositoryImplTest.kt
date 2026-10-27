package com.example.tp3_parcial_grupo_1.data.repository

import com.example.tp3_parcial_grupo_1.data.local.dao.ExerciseDao
import com.example.tp3_parcial_grupo_1.data.local.dao.WorkoutDao
import com.example.tp3_parcial_grupo_1.data.local.entity.ExerciseEntity
import com.example.tp3_parcial_grupo_1.data.local.entity.WorkoutEntity
import com.example.tp3_parcial_grupo_1.data.remote.api.WorkoutApi
import com.example.tp3_parcial_grupo_1.data.remote.dto.DataResponse
import com.example.tp3_parcial_grupo_1.data.remote.dto.ExerciseDto
import com.example.tp3_parcial_grupo_1.data.remote.dto.WeeklyPlanDto
import com.example.tp3_parcial_grupo_1.data.remote.dto.WorkoutDto
import com.example.tp3_parcial_grupo_1.data.remote.dto.WorkoutSummaryDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.test.runTest
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import retrofit2.Response

class WorkoutRepositoryImplTest {

    private lateinit var workoutDao: FakeWorkoutDao
    private lateinit var exerciseDao: FakeExerciseDao
    private lateinit var workoutApi: FakeWorkoutApi
    private lateinit var repository: WorkoutRepositoryImpl

    @Before
    fun setUp() {
        workoutDao = FakeWorkoutDao()
        exerciseDao = FakeExerciseDao()
        workoutApi = FakeWorkoutApi()
        repository = WorkoutRepositoryImpl(workoutDao, exerciseDao, workoutApi)
    }

    @Test
    fun `refreshWorkouts updates local dao on success`() = runTest {
        val summary = WorkoutSummaryDto("w01", "Upper Body", "MODERATE", 45, 5, "Chest")
        workoutApi.workoutsResponse = Response.success(DataResponse(listOf(summary)))

        val result = repository.refreshWorkouts()

        assertTrue(result.isSuccess)
        val stored = workoutDao.getAllWorkouts().first()
        assertEquals(1, stored.size)
        assertEquals("w01", stored.first().id)
    }

    @Test
    fun `refreshWorkoutDetails inserts workout and exercises on success`() = runTest {
        val exerciseDto = ExerciseDto("e01", "Bench Press", "Chest", 3, 10, 90, "Steps", "Tip")
        val workoutDto = WorkoutDto("w01", "Upper Body", "MODERATE", 45, "Chest", listOf(exerciseDto))
        workoutApi.workoutDetailResponse = Response.success(DataResponse(workoutDto))

        val result = repository.refreshWorkoutDetails("w01")

        assertTrue(result.isSuccess)
        val workoutStored = workoutDao.getWorkoutById("w01").first()
        assertEquals("w01", workoutStored?.id)
        val exercisesStored = exerciseDao.getExercisesByWorkout("w01").first()
        assertEquals(1, exercisesStored.size)
        assertEquals("e01", exercisesStored.first().id)
    }

    private class FakeWorkoutDao : WorkoutDao {
        val workouts = MutableStateFlow<List<WorkoutEntity>>(emptyList())

        override fun getAllWorkouts(): Flow<List<WorkoutEntity>> = workouts

        override fun getWorkoutById(id: String): Flow<WorkoutEntity?> =
            workouts.map { list -> list.find { it.id == id } }

        override suspend fun insert(workout: WorkoutEntity) {
            workouts.value = workouts.value.filterNot { it.id == workout.id } + workout
        }

        override suspend fun delete(id: String) {
            workouts.value = workouts.value.filterNot { it.id == id }
        }
    }

    private class FakeExerciseDao : ExerciseDao {
        val exercises = MutableStateFlow<List<ExerciseEntity>>(emptyList())

        override fun getExercisesByWorkout(workoutId: String): Flow<List<ExerciseEntity>> =
            exercises.map { list -> list.filter { it.workoutId == workoutId } }

        override suspend fun insertExercises(exercises: List<ExerciseEntity>) {
            this.exercises.value = this.exercises.value + exercises
        }

        override suspend fun deleteExercisesByWorkout(workoutId: String) {
            exercises.value = exercises.value.filterNot { it.workoutId == workoutId }
        }

        override fun getExerciseById(workoutId: String, exerciseId: String): Flow<ExerciseEntity?> =
            exercises.map { list -> list.find { it.workoutId == workoutId && it.id == exerciseId } }
    }

    private class FakeWorkoutApi : WorkoutApi {
        var workoutsResponse: Response<DataResponse<List<WorkoutSummaryDto>>> = Response.success(DataResponse(emptyList()))
        var workoutDetailResponse: Response<DataResponse<WorkoutDto>>? = null

        override suspend fun getWorkouts(): Response<DataResponse<List<WorkoutSummaryDto>>> = workoutsResponse

        override suspend fun getWorkoutById(id: String): Response<DataResponse<WorkoutDto>> {
            return workoutDetailResponse ?: Response.error(404, "".toResponseBody(null))
        }

        override suspend fun getWeeklyPlan(): Response<DataResponse<WeeklyPlanDto>> {
            return Response.success(DataResponse(WeeklyPlanDto()))
        }
    }
}
