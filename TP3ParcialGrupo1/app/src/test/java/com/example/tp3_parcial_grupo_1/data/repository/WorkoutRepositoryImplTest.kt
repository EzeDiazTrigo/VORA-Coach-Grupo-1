package com.example.tp3_parcial_grupo_1.data.repository

import com.example.tp3_parcial_grupo_1.data.local.dao.ExerciseDao
import com.example.tp3_parcial_grupo_1.data.local.dao.WorkoutDao
import com.example.tp3_parcial_grupo_1.data.local.entity.ExerciseEntity
import com.example.tp3_parcial_grupo_1.data.local.entity.WorkoutEntity
import com.example.tp3_parcial_grupo_1.data.remote.api.WorkoutApi
import com.example.tp3_parcial_grupo_1.data.remote.dto.ExerciseDto
import com.example.tp3_parcial_grupo_1.data.remote.dto.WorkoutDto
import com.example.tp3_parcial_grupo_1.data.remote.dto.WorkoutSummaryDto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import retrofit2.Response

class WorkoutRepositoryImplTest {

    private lateinit var fakeWorkoutDao: FakeWorkoutDao
    private lateinit var fakeExerciseDao: FakeExerciseDao
    private lateinit var fakeWorkoutApi: FakeWorkoutApi
    private lateinit var repository: WorkoutRepositoryImpl

    @Before
    fun setUp() {
        fakeWorkoutDao = FakeWorkoutDao()
        fakeExerciseDao = FakeExerciseDao()
        fakeWorkoutApi = FakeWorkoutApi()
        repository = WorkoutRepositoryImpl(fakeWorkoutDao, fakeExerciseDao, fakeWorkoutApi)
    }

    @Test
    fun `refreshWorkouts fetches from API and inserts into DAO`() = runBlocking {
        fakeWorkoutApi.workoutsResponse = Response.success(
            listOf(
                WorkoutSummaryDto("w01", "Fuerza", "Intermedio", 45, 3, "Desc")
            )
        )

        val result = repository.refreshWorkouts()

        assertTrue(result.isSuccess)
        val cached = fakeWorkoutDao.getAllWorkouts().first()
        assertEquals(1, cached.size)
        assertEquals("w01", cached.first().id)
        assertEquals("Fuerza", cached.first().name)
    }

    @Test
    fun `refreshWorkoutDetails fetches detail from API and inserts exercises into DAO`() = runBlocking {
        fakeWorkoutApi.workoutDetailResponse = Response.success(
            WorkoutDto(
                id = "w01",
                name = "Fuerza",
                level = "Intermedio",
                durationMinutes = 45,
                description = "Desc",
                exercises = listOf(
                    ExerciseDto("e01", "Sentadilla", "Piernas", 4, 10, 60, "Inst", "Tip")
                )
            )
        )

        val result = repository.refreshWorkoutDetails("w01")

        assertTrue(result.isSuccess)
        val cachedWorkout = fakeWorkoutDao.getWorkoutById("w01").first()
        assertEquals("w01", cachedWorkout?.id)

        val cachedExercises = fakeExerciseDao.getExercisesByWorkout("w01").first()
        assertEquals(1, cachedExercises.size)
        assertEquals("e01", cachedExercises.first().id)
        assertEquals("Sentadilla", cachedExercises.first().name)
    }

    // Fake implementations
    private class FakeWorkoutDao : WorkoutDao {
        private val workouts = MutableStateFlow<Map<String, WorkoutEntity>>(emptyMap())

        override fun getAllWorkouts(): Flow<List<WorkoutEntity>> = workouts.map { it.values.toList() }

        override fun getWorkoutById(id: String): Flow<WorkoutEntity?> = workouts.map { it[id] }

        override suspend fun insert(workout: WorkoutEntity) {
            workouts.value = workouts.value + (workout.id to workout)
        }

        override suspend fun delete(id: String) {
            workouts.value = workouts.value - id
        }
    }

    private class FakeExerciseDao : ExerciseDao {
        private val exercises = MutableStateFlow<List<ExerciseEntity>>(emptyList())

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
        var workoutsResponse: Response<List<WorkoutSummaryDto>> = Response.success(emptyList())
        var workoutDetailResponse: Response<WorkoutDto>? = null

        override suspend fun getWorkouts(): Response<List<WorkoutSummaryDto>> = workoutsResponse

        override suspend fun getWorkoutById(id: String): Response<WorkoutDto> {
            return workoutDetailResponse ?: Response.error(404, okhttp3.ResponseBody.create(null, ""))
        }
    }
}
