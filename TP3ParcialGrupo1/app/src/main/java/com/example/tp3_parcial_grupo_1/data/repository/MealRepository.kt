package com.example.tp3_parcial_grupo_1.data.repository

import com.example.tp3_parcial_grupo_1.data.local.dao.MealDao
import com.example.tp3_parcial_grupo_1.data.local.entity.MealEntity
import com.example.tp3_parcial_grupo_1.model.Meal
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MealRepository @Inject constructor(
    private val mealDao: MealDao
) {
    fun observeMealsByDate(date: String): Flow<List<Meal>> {
        return mealDao.observeByDate(date).map { entities ->
            entities.map { it.toDomain() }
        }
    }

    fun observeAllMeals(): Flow<List<Meal>> {
        return mealDao.observeAll().map { entities ->
            entities.map { it.toDomain() }
        }
    }

    suspend fun getMealById(id: Long): Meal? {
        return mealDao.getById(id)?.toDomain()
    }

    suspend fun insertMeal(meal: Meal): Long {
        return mealDao.insert(MealEntity.fromDomain(meal))
    }

    suspend fun deleteMeal(id: Long) {
        mealDao.deleteById(id)
    }

    suspend fun duplicateMeal(id: Long): Long? {
        val entity = mealDao.getById(id) ?: return null
        val copy = entity.copy(id = 0L)
        return mealDao.insert(copy)
    }
}
