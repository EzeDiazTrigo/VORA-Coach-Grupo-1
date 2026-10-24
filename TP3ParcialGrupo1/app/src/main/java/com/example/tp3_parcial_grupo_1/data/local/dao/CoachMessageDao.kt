package com.example.tp3_parcial_grupo_1.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.tp3_parcial_grupo_1.data.local.entity.CoachMessageEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CoachMessageDao {
    @Query("SELECT * FROM coach_messages ORDER BY createdAt, id")
    fun observeAll(): Flow<List<CoachMessageEntity>>

    @Insert
    suspend fun insert(message: CoachMessageEntity): Long
}
