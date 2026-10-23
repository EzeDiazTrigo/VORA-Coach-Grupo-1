package com.example.tp3_parcial_grupo_1.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.tp3_parcial_grupo_1.data.local.entity.MealEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MealDao {
    @Query("SELECT * FROM meals WHERE date = :date")
    fun observeByDate(date: String): Flow<List<MealEntity>>

    @Query("SELECT * FROM meals")
    fun observeAll(): Flow<List<MealEntity>>

    @Query("SELECT * FROM meals WHERE id = :id")
    suspend fun getById(id: Long): MealEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(meal: MealEntity): Long

    @Query("DELETE FROM meals WHERE id = :id")
    suspend fun deleteById(id: Long)

    @Query("DELETE FROM meals")
    suspend fun deleteAll()
}
